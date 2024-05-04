package ir.aliranjbarzadeh.finances.presentation

enum class TransactionType(val type: String) {
	DEPOSIT("deposit"),
	WITHDRAW("withdraw");

	companion object {
		fun from(type: String): TransactionType = TransactionType.valueOf(type.uppercase())
	}
}