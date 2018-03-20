import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

import Utils.Utils;

public class WatchEngine {

    private static String watchDirectory = "/Users/ioni/streaming_logs/work_dir";

    private static void assertFolderPath(Path path) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path,"basic:isDirectory", NOFOLLOW_LINKS);

            if (!isFolder)
                throw new IllegalArgumentException("Path: " + path + " is not a folder");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void watchDirectory(Path path) {

        assertFolderPath(path);

        WatchKey key = null;
        Kind<?> kind = null;

        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {

            path.register(service, ENTRY_CREATE);

            while (true) {

                Logger.out("Ready to watch on new archives...");

                /* This call blocks the current thread until an event occurs */
                key = service.take();

                Logger.out("New file detected! Start processing the archive...");

                /* Iterate over the events and process each one */
                for (WatchEvent<?> watchEvent : key.pollEvents()) {

                    kind = watchEvent.kind();

                    if (kind == OVERFLOW)                     /* If the events are lost or discarded */
                        continue;

                    else if (kind == ENTRY_CREATE) {          /* If a new file is added to the directory */
                        Path created_entity = ((WatchEvent<Path>) watchEvent).context();

                        if(Utils.isArchive(created_entity.toString())) {
                            ParseEngine.ParseArchive(Paths.get(watchDirectory).resolve(created_entity));    /* We do this because of a bug in WatchService */
                        }
                    }
                }

                if (!key.reset())
                    break;
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        while(args.length != 2 || !Utils.checkInput(args[0], args[1])) {
            System.out.println("Usage: java -jar Diploma-Project.jar --watch-directory=/path/to/in/dir --destination-directory=/path/to/out/dir");
            return;
        }

        WatchEngine.watchDirectory = args[0].split("=")[1];
        DataRepository.setDestinationDirectory(args[1].split("=")[1]);

        File dir = new File(WatchEngine.watchDirectory);

        watchDirectory(dir.toPath());
    }
}
