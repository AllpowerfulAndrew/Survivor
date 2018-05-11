package model.constants

enum class Action(private val longCommand: String, private val shortCommand: String) {
    NORTH("север", "с"),
    SOUTH("юг", "ю"),
    WEST("запад", "з"),
    EAST("восток", "в"),
    TAKE("взять", "вз"),
    DROP("выбросить", "выб"),
    USE("использовать", "исп"),
    OPEN("открыть", "отк"),
    INVESTIGATE("изучить", "из"),
    LOOK_AROUND("осмотреться", "осм"),
    INVENTORY("инвентарь", "инв"),
    HELP("помощь", "пом");

    fun key()= toString().toLowerCase().replace("_", " ")

    fun hasCommand(command: String) = longCommand == command || shortCommand == command
}
