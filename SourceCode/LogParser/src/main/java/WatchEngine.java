import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

import Utils.Utils;

public class WatchEngine {

    private static String watchDirectory = "";
    private static String destinationDirectory = "";

    private static void assertFolderPath(Path path) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path,"basic:isDirectory", NOFOLLOW_LINKS);

            if (!isFolder)
                throw new IllegalArgumentException("Path: " + path + " is not a folder");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void processEvent(WatchEvent<?> watchEvent) {
        Kind<?> kind = watchEvent.kind();

        if (kind == ENTRY_CREATE) {          /* If a new entry is added to the directory (it can be a directory) */
            Path created_entity = ((WatchEvent<Path>) watchEvent).context();

            if(Utils.isArchive(created_entity.toString())) {
                Logger.out("Processing the archive: " + created_entity.toString());

                ParseEngine.ParseArchive(Paths.get(watchDirectory).resolve(created_entity), destinationDirectory);
            }
        }
    }

    private static void watchDirectory(Path path) {
        WatchKey key;

        assertFolderPath(path);

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
                    processEvent(watchEvent);
                }

                if (!key.reset())
                    break;
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length != 2 || !Utils.checkInput(args[0], args[1])) {
            Logger.out("Usage: java -jar Diploma-Project.jar --watch-directory=/path/to/in/dir --destination-directory=/path/to/out/dir");
            return;
        }

        WatchEngine.watchDirectory = args[0].split("=")[1];
        WatchEngine.destinationDirectory = args[1].split("=")[1];

        watchDirectory(Paths.get(WatchEngine.watchDirectory));
    }
}
