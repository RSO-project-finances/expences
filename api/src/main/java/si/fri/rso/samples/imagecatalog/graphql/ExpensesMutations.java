package si.fri.rso.samples.imagecatalog.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import si.fri.rso.samples.imagecatalog.lib.Expenses;
import si.fri.rso.samples.imagecatalog.services.beans.ExpensesBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class ExpensesMutations {

    @Inject
    private ExpensesBean expensesBean;

    @GraphQLMutation
    public Expenses addExpense(@GraphQLArgument(name = "expense") Expenses expense) {
        expensesBean.createExpense(expense);
        return expense;
    }

    @GraphQLMutation
    public DeleteResponse deleteExpense(@GraphQLArgument(name = "id") Integer id) {
        return new DeleteResponse(expensesBean.deleteExpense(id));
    }

}
