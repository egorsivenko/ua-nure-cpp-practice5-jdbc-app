package ua.nure.cpp.sivenko.practice5.dao;

import ua.nure.cpp.sivenko.practice5.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice5.model.PawnTransaction.TransactionStatus;

import java.util.List;

public interface PawnTransactionDAO {
    PawnTransaction getPawnTransactionById(long pawnTransactionId);
    List<PawnTransaction> getPawnTransactionsByCustomerId(long customerId);
    List<PawnTransaction> getPawnTransactionsByStatus(TransactionStatus transactionStatus);
    List<PawnTransaction> getAllPawnTransactions();

    void addPawnTransaction(PawnTransaction pawnTransaction);
    void deletePawnTransaction(long pawnTransactionId);
}
