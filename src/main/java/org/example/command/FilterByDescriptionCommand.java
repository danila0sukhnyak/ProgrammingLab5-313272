package org.example.command;

public class FilterByDescriptionCommand extends AbstractCommand {

    @Override
    public String command() {
        return null;
    }

    @Override
    public String description() {
        return "Вывести элементы, значение поля description которых меньше заданного";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return;
        }
        musicBandDAO.filterByDescription(args[1]).forEach(s -> {
            if (s != null)
                consoleService.printLn(s.toString());
        });
    }
}
