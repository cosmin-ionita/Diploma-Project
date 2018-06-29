package Utils;

import Gui.GuiManager;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    private final static String bucketName = "aam-ioni-qe-1-test-logs";

    public static void showGUI() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                javafx.application.Application.launch(GuiManager.class);
            }
        });

        t.start();
    }

    public static void wait(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        }catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private static String printUTCDate(String timeStamp) {
        try {
            DateFormat sourceFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
            DateFormat destinationFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

            destinationFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = sourceFormat.parse(timeStamp);

            String formattedDate = destinationFormat.format(date);

            return formattedDate + " ";
        }
        catch (ParseException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static String getDocumentsSerialized(SolrDocumentList documents) {
        String result = "";

        for(SolrDocument document : documents) {
            Collection<String> fields = document.getFieldNames();

            for(String field : fields) {
                if(field.equals("timeStamp"))
                    result += printUTCDate(document.get(field).toString());
                else
                    result += document.get(field) + " ";
            }

            result += "\n";
        }

        return result;
    }

    private static ByteArrayInputStream getByteArrayInputStream(File file) throws IOException {
        return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
    }

    public static void uploadTestArchive(){
        String archive = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/Client/src/test/TestResources/archive.zip";

        try {
            AmazonS3 client = AmazonS3ClientBuilder.defaultClient();

            File file = new File(archive);

            ByteArrayInputStream stream = getByteArrayInputStream(file);

            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(file.length());

            metadata.setContentType("application/octet-stream");

            client.putObject(bucketName, file.getName(), stream, metadata);

        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Options buildOptions() {
        Options options = new Options();

        Option queryOption = Option.builder(StringsMapping.queryShort)
                .required(false)
                .hasArg(true)
                .hasArgs()
                .longOpt(StringsMapping.queryLong)
                .desc(StringsMapping.queryDescription)
                .build();

        Option timeOption = Option.builder(StringsMapping.timeIntervalShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(2)
                .longOpt(StringsMapping.timeIntervalLong)
                .desc(StringsMapping.timeIntervalDescription)
                .build();

        Option dateOption = Option.builder(StringsMapping.dateIntervalShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(2)
                .longOpt(StringsMapping.dateIntervalLong)
                .desc(StringsMapping.dateIntervalDescription)
                .build();

        Option orderByTimeStamp = Option.builder(StringsMapping.orderByTimeStampShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(1)
                .longOpt(StringsMapping.orderByTimeStampLong)
                .desc(StringsMapping.orderByTimeStampDescription)
                .build();

        Option export = Option.builder(StringsMapping.exportShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(1)
                .longOpt(StringsMapping.exportLong)
                .desc(StringsMapping.exportDescription)
                .build();

        Option gui = Option.builder(StringsMapping.guiShort)
                .required(false)
                .hasArg(false)
                .numberOfArgs(0)
                .longOpt(StringsMapping.guiLong)
                .desc(StringsMapping.guiDescription)
                .build();

        Option fields = Option.builder(StringsMapping.fieldsShort)
                .required(false)
                .hasArg(false)
                .numberOfArgs(0)
                .longOpt(StringsMapping.fieldsLong)
                .desc(StringsMapping.fieldsDescription)
                .build();

        Option status = Option.builder(StringsMapping.statusShort)
                .required(false)
                .hasArg(false)
                .numberOfArgs(0)
                .longOpt(StringsMapping.statusLong)
                .desc(StringsMapping.statusDescription)
                .build();

        Option indexInterval = Option.builder(StringsMapping.indexIntervalShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(1)
                .longOpt(StringsMapping.indexIntervalLong)
                .desc(StringsMapping.indexIntervalDescription)
                .build();

        Option indexNow = Option.builder(StringsMapping.indexNowShort)
                .required(false)
                .hasArg(false)
                .numberOfArgs(0)
                .longOpt(StringsMapping.indexNowLong)
                .desc(StringsMapping.indexNowDescription)
                .build();

//        Option init = Option.builder(null)
//                .required(false)
//                .hasArg(false)
//                .numberOfArgs(0)
//                .longOpt(StringsMapping.initCommand)
//                .desc(StringsMapping.initCommandDescription)
//                .build();

        options.addOption(queryOption);
        options.addOption(timeOption);
        options.addOption(dateOption);
        options.addOption(orderByTimeStamp);
        options.addOption(export);

        options.addOption(gui);
        options.addOption(fields);
        options.addOption(status);
        options.addOption(indexInterval);
        options.addOption(indexNow);
        //options.addOption(init);

        return options;
    }
}
