package Enums;

public enum ArchiveTypes {
    TAR (".tar"),
    ZIP (".zip"),
    GZIP (".gzip");


    private String archiveExtension;

    ArchiveTypes(String archiveExtension) {
        this.archiveExtension = archiveExtension;
    }

    public String toString(ArchiveTypes type) {
        return type.archiveExtension;
    }
}
