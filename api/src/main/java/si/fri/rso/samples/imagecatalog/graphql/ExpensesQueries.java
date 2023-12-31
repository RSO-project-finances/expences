package si.fri.rso.samples.imagecatalog.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.samples.imagecatalog.lib.Expenses;
import si.fri.rso.samples.imagecatalog.services.beans.ExpensesBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class ExpensesQueries {

    @Inject
    private ExpensesBean expensesBean;

    @GraphQLQuery
    public PaginationWrapper<Expenses> allExpenses(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                             @GraphQLArgument(name = "sort") Sort sort,
                                                             @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(expensesBean.getExpenses(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Expenses getExpenses(@GraphQLArgument(name = "id") Integer id) {
        return expensesBean.getExpenses(id);
    }

}
