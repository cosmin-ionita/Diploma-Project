import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchEngine {

    private static final String target_directory = "/Users/ioni/streaming_logs/";

    private static void assertFolderPath(Path path) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path,"basic:isDirectory", NOFOLLOW_LINKS);

            if (!isFolder)
                throw new IllegalArgumentException("Path: " + path + " is not a folder");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void watchDirectoryPath(Path path) {

        assertFolderPath(path);

        WatchKey key = null;
        Kind<?> kind = null;

        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {

            path.register(service, ENTRY_CREATE);

            while (true) {

                /* This call blocks the control until an event occurs */
                key = service.take();

                /* Iterate over the events and process each one */
                for (WatchEvent<?> watchEvent : key.pollEvents()) {

                    kind = watchEvent.kind();

                    if (OVERFLOW == kind) {      /* If the events are lost or discarded */
                        continue;
                                                /* If a new file is added to the directory */
                    } else if (ENTRY_CREATE == kind) {
                        Path created_file = ((WatchEvent<Path>) watchEvent).context();

                        ParseEngine.ParseArchive(created_file);
                    }
                }

                if (!key.reset()) {
                    break;
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        File dir = new File(WatchEngine.target_directory);
        watchDirectoryPath(dir.toPath());
    }
}
