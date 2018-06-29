package Factories;

import Commands.*;
import Interfaces.Command;
import Interfaces.Query;
import Queries.*;
import Utils.StringsMapping;

public class CommandFactory {

    public Query getQuery(String name) {

        if(name.equals(StringsMapping.queryShort)) {
            return new SimpleQuery();

        } else if(name.equals(StringsMapping.dateIntervalShort)) {
            return new DateInterval();

        } else if(name.equals(StringsMapping.timeIntervalShort)) {
            return new TimeInterval();

        } else if(name.equals(StringsMapping.orderByTimeStampShort)) {
            return new OrderByTimeStamp();
        }

        return null;
    }

    public Command getCommand(String name) {

        if(name.equals(StringsMapping.fieldsShort)) {
            return new FieldsCommand();

        } else if(name.equals(StringsMapping.statusShort)) {
            return new StatusCommand();

        } else if(name.equals(StringsMapping.indexIntervalShort)) {
            return new IndexIntervalCommand();

        } else if(name.equals(StringsMapping.indexNowShort)) {
            return new IndexNowCommand();

        } else if(name.equals(StringsMapping.guiShort)) {
            return new GuiCommand();

        } else if(name.equals(StringsMapping.exportShort)) {
            return new ExportCommand();
        }

        return null;
    }
}
