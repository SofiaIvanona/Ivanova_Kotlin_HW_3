// Импортируем библиотеку для работы с вводом/выводом
import java.io.*

// Основная функция программы
fun main() {
    // Создаем экземпляр класса ExpenseTracker для отслеживания финансовых операций
    val tracker = ExpenseTracker()

    // Бесконечный цикл для отображения меню и обработки выбора пользователя
    while (true) {
        // Отображаем меню пользователя
        println("\nMenu:")
        println("1. Show balance") // Показать баланс
        println("2. Add expense") // Добавить расход
        println("3. Add income") // Добавить доход
        println("4. Undo last transaction") // Отменить последнюю транзакцию
        println("5. Exit") // Выйти из программы
        print("Enter your choice: ") // Предложение ввести выбор

        try {
            // Чтение выбора пользователя и выполнение соответствующего действия
            when (readLine()!!.toInt()) {
                1 -> tracker.showBalance()
                2 -> {
                    // Запрос описания и суммы расхода, добавление расхода
                    print("Enter expense description: ")
                    val description = readLine()!!
                    print("Enter amount: ")
                    val amount = readLine()!!.toDoubleOrNull() ?: throw IllegalArgumentException("Amount must be a number.")
                    tracker.addTransaction(description, -amount)
                }
                3 -> {
                    // Запрос описания и суммы дохода, добавление дохода
                    print("Enter income description: ")
                    val description = readLine()!!
                    print("Enter amount: ")
                    val amount = readLine()!!.toDoubleOrNull() ?: throw IllegalArgumentException("Amount must be a number.")
                    tracker.addTransaction(description, amount)
                }
                4 -> tracker.undoLastTransaction() // Отменить последнюю транзакцию
                5 -> return // Выйти из программы
                else -> println("Invalid option. Please try again.") // Обработка неверного выбора
            }
        } catch (e: NumberFormatException) {
            // Обработка ошибки, если ввод не является числом
            println("Error: Invalid input. Please enter numbers only.")
        } catch (e: IllegalArgumentException) {
            // Обработка других ошибок ввода
            println("Error: ${e.message}")
        }
    }
}

// Класс для отслеживания финансовых операций
class ExpenseTracker {
    private var balance: Double = 0.0 // Текущий баланс
    private val transactions = mutableListOf<Transaction>() // Список всех транзакций

    // Метод для добавления транзакции (дохода или расхода)
    fun addTransaction(description: String, amount: Double) {
        if (amount == 0.0) throw IllegalArgumentException("Amount cannot be zero.") // Проверка, что сумма не равна нулю
        transactions.add(Transaction(description, amount)) // Добавление транзакции в список
        balance += amount // Обновление баланса
        // Вывод сообщения о успешном добавлении операции
        if (amount > 0) {
            println("Income added successfully.")
        } else {
            println("Expense added successfully.")
        }
    }

    // Метод для отображения текущего баланса и всех транзакций
    fun showBalance() {
        println("Current balance: $balance") // Вывод текущего баланса
        if (transactions.isNotEmpty()) {
            println("Transactions:") // Вывод списка транзакций
            transactions.forEach {
                val type = if (it.amount > 0) "Income" else "Expense" // Определение типа транзакции
                println("$type - ${it.description}: ${it.amount}") // Вывод описания и суммы транзакции
            }
        }
    }

    // Метод для отмены последней транзакции

    fun undoLastTransaction() {
        // Проверяем, есть ли транзакции для отмены
        if (transactions.isNotEmpty()) {
            // Удаляем последнюю транзакцию из списка
            val lastTransaction = transactions.removeLast()
            // Корректируем баланс, отменяя последнюю транзакцию
            balance -= lastTransaction.amount
            // Определяем тип последней транзакции для вывода информации
            val type = if (lastTransaction.amount > 0) "Income" else "Expense"
            // Выводим информацию об отмененной транзакции
            println("Last transaction undone. Type: $type, Description: ${lastTransaction.description}, Amount: ${lastTransaction.amount}")
        } else {
            // В случае, если транзакций для отмены нет, выводим сообщение
            println("No transactions to undo.")
        }
    }

    // Внутренний класс для представления транзакции (дохода или расхода)
    private data class Transaction(val description: String, val amount: Double)
}

