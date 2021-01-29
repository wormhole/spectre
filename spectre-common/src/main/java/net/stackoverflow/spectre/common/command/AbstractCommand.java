package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.*;

/**
 * shell命令
 *
 * @author wormhole
 */
public abstract class AbstractCommand implements Command {

    protected String command;

    protected String description;

    protected Receiver receiver;

    protected Options options = new Options();

    protected CommandLineParser parser = new DefaultParser();

    protected HelpFormatter formatter = new HelpFormatter();

    public AbstractCommand(String command, String description, Receiver receiver) {
        this.command = command;
        this.description = description;
        this.receiver = receiver;
        initOption();
    }

    public String command() {
        return this.command;
    }

    public String description() {
        return this.description;
    }

    public Object execute(String... args) {
        String[] opts = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            opts[i - 1] = args[i];
        }
        Object result = null;
        try {
            parser.parse(options, opts);
            result = receiver.action(args);
        } catch (ParseException e) {
            formatter.printHelp(command, options, true);
        }
        return result;
    }

    abstract protected void initOption();
}
