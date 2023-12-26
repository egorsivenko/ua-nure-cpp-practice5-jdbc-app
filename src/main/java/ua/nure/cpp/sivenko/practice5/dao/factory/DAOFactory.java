package ua.nure.cpp.sivenko.practice5.dao.factory;

import ua.nure.cpp.sivenko.practice5.dao.*;

public interface DAOFactory {
    CustomerDAO getCustomerDAO();
    ItemDAO getItemDAO();
    ItemCategoryDAO getItemCategoryDAO();
    PawnbrokerDAO getPawnbrokerDAO();
    PawnTransactionDAO getPawnTransactionDAO();
    RepaymentDAO getRepaymentDAO();
    PaymentMethodDAO getPaymentMethodDAO();
}
