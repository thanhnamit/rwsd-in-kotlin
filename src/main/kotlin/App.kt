import com.tna.BankStatementCSVParser
import com.tna.BankTransactionAnalyzer

fun main(args: Array<String>) {
    BankTransactionAnalyzer().analyze(args[0], BankStatementCSVParser())
}