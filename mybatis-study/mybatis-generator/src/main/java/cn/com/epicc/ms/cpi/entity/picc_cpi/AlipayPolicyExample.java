package cn.com.epicc.ms.cpi.entity.picc_cpi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AlipayPolicyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AlipayPolicyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdIsNull() {
            addCriterion("OUT_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdIsNotNull() {
            addCriterion("OUT_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdEqualTo(String value) {
            addCriterion("OUT_ORDER_ID =", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdNotEqualTo(String value) {
            addCriterion("OUT_ORDER_ID <>", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdGreaterThan(String value) {
            addCriterion("OUT_ORDER_ID >", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("OUT_ORDER_ID >=", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdLessThan(String value) {
            addCriterion("OUT_ORDER_ID <", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdLessThanOrEqualTo(String value) {
            addCriterion("OUT_ORDER_ID <=", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdLike(String value) {
            addCriterion("OUT_ORDER_ID like", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdNotLike(String value) {
            addCriterion("OUT_ORDER_ID not like", value, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdIn(List<String> values) {
            addCriterion("OUT_ORDER_ID in", values, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdNotIn(List<String> values) {
            addCriterion("OUT_ORDER_ID not in", values, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdBetween(String value1, String value2) {
            addCriterion("OUT_ORDER_ID between", value1, value2, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andOutOrderIdNotBetween(String value1, String value2) {
            addCriterion("OUT_ORDER_ID not between", value1, value2, "outOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdIsNull() {
            addCriterion("SUMMARY_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdIsNotNull() {
            addCriterion("SUMMARY_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdEqualTo(String value) {
            addCriterion("SUMMARY_ORDER_ID =", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdNotEqualTo(String value) {
            addCriterion("SUMMARY_ORDER_ID <>", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdGreaterThan(String value) {
            addCriterion("SUMMARY_ORDER_ID >", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUMMARY_ORDER_ID >=", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdLessThan(String value) {
            addCriterion("SUMMARY_ORDER_ID <", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdLessThanOrEqualTo(String value) {
            addCriterion("SUMMARY_ORDER_ID <=", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdLike(String value) {
            addCriterion("SUMMARY_ORDER_ID like", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdNotLike(String value) {
            addCriterion("SUMMARY_ORDER_ID not like", value, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdIn(List<String> values) {
            addCriterion("SUMMARY_ORDER_ID in", values, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdNotIn(List<String> values) {
            addCriterion("SUMMARY_ORDER_ID not in", values, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdBetween(String value1, String value2) {
            addCriterion("SUMMARY_ORDER_ID between", value1, value2, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andSummaryOrderIdNotBetween(String value1, String value2) {
            addCriterion("SUMMARY_ORDER_ID not between", value1, value2, "summaryOrderId");
            return (Criteria) this;
        }

        public Criteria andProdNoIsNull() {
            addCriterion("PROD_NO is null");
            return (Criteria) this;
        }

        public Criteria andProdNoIsNotNull() {
            addCriterion("PROD_NO is not null");
            return (Criteria) this;
        }

        public Criteria andProdNoEqualTo(String value) {
            addCriterion("PROD_NO =", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoNotEqualTo(String value) {
            addCriterion("PROD_NO <>", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoGreaterThan(String value) {
            addCriterion("PROD_NO >", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_NO >=", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoLessThan(String value) {
            addCriterion("PROD_NO <", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoLessThanOrEqualTo(String value) {
            addCriterion("PROD_NO <=", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoLike(String value) {
            addCriterion("PROD_NO like", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoNotLike(String value) {
            addCriterion("PROD_NO not like", value, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoIn(List<String> values) {
            addCriterion("PROD_NO in", values, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoNotIn(List<String> values) {
            addCriterion("PROD_NO not in", values, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoBetween(String value1, String value2) {
            addCriterion("PROD_NO between", value1, value2, "prodNo");
            return (Criteria) this;
        }

        public Criteria andProdNoNotBetween(String value1, String value2) {
            addCriterion("PROD_NO not between", value1, value2, "prodNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoIsNull() {
            addCriterion("OUT_PROD_NO is null");
            return (Criteria) this;
        }

        public Criteria andOutProdNoIsNotNull() {
            addCriterion("OUT_PROD_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOutProdNoEqualTo(String value) {
            addCriterion("OUT_PROD_NO =", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoNotEqualTo(String value) {
            addCriterion("OUT_PROD_NO <>", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoGreaterThan(String value) {
            addCriterion("OUT_PROD_NO >", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoGreaterThanOrEqualTo(String value) {
            addCriterion("OUT_PROD_NO >=", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoLessThan(String value) {
            addCriterion("OUT_PROD_NO <", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoLessThanOrEqualTo(String value) {
            addCriterion("OUT_PROD_NO <=", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoLike(String value) {
            addCriterion("OUT_PROD_NO like", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoNotLike(String value) {
            addCriterion("OUT_PROD_NO not like", value, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoIn(List<String> values) {
            addCriterion("OUT_PROD_NO in", values, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoNotIn(List<String> values) {
            addCriterion("OUT_PROD_NO not in", values, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoBetween(String value1, String value2) {
            addCriterion("OUT_PROD_NO between", value1, value2, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andOutProdNoNotBetween(String value1, String value2) {
            addCriterion("OUT_PROD_NO not between", value1, value2, "outProdNo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeIsNull() {
            addCriterion("POLICY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeIsNotNull() {
            addCriterion("POLICY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeEqualTo(BigDecimal value) {
            addCriterion("POLICY_TYPE =", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotEqualTo(BigDecimal value) {
            addCriterion("POLICY_TYPE <>", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeGreaterThan(BigDecimal value) {
            addCriterion("POLICY_TYPE >", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POLICY_TYPE >=", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeLessThan(BigDecimal value) {
            addCriterion("POLICY_TYPE <", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POLICY_TYPE <=", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeIn(List<BigDecimal> values) {
            addCriterion("POLICY_TYPE in", values, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotIn(List<BigDecimal> values) {
            addCriterion("POLICY_TYPE not in", values, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POLICY_TYPE between", value1, value2, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POLICY_TYPE not between", value1, value2, "policyType");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeIsNull() {
            addCriterion("INSURED_TIME is null");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeIsNotNull() {
            addCriterion("INSURED_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeEqualTo(String value) {
            addCriterion("INSURED_TIME =", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeNotEqualTo(String value) {
            addCriterion("INSURED_TIME <>", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeGreaterThan(String value) {
            addCriterion("INSURED_TIME >", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeGreaterThanOrEqualTo(String value) {
            addCriterion("INSURED_TIME >=", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeLessThan(String value) {
            addCriterion("INSURED_TIME <", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeLessThanOrEqualTo(String value) {
            addCriterion("INSURED_TIME <=", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeLike(String value) {
            addCriterion("INSURED_TIME like", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeNotLike(String value) {
            addCriterion("INSURED_TIME not like", value, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeIn(List<String> values) {
            addCriterion("INSURED_TIME in", values, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeNotIn(List<String> values) {
            addCriterion("INSURED_TIME not in", values, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeBetween(String value1, String value2) {
            addCriterion("INSURED_TIME between", value1, value2, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andInsuredTimeNotBetween(String value1, String value2) {
            addCriterion("INSURED_TIME not between", value1, value2, "insuredTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeIsNull() {
            addCriterion("ISSUE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andIssueTimeIsNotNull() {
            addCriterion("ISSUE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andIssueTimeEqualTo(String value) {
            addCriterion("ISSUE_TIME =", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotEqualTo(String value) {
            addCriterion("ISSUE_TIME <>", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeGreaterThan(String value) {
            addCriterion("ISSUE_TIME >", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ISSUE_TIME >=", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeLessThan(String value) {
            addCriterion("ISSUE_TIME <", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeLessThanOrEqualTo(String value) {
            addCriterion("ISSUE_TIME <=", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeLike(String value) {
            addCriterion("ISSUE_TIME like", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotLike(String value) {
            addCriterion("ISSUE_TIME not like", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeIn(List<String> values) {
            addCriterion("ISSUE_TIME in", values, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotIn(List<String> values) {
            addCriterion("ISSUE_TIME not in", values, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeBetween(String value1, String value2) {
            addCriterion("ISSUE_TIME between", value1, value2, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotBetween(String value1, String value2) {
            addCriterion("ISSUE_TIME not between", value1, value2, "issueTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeIsNull() {
            addCriterion("SURRENDER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeIsNotNull() {
            addCriterion("SURRENDER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeEqualTo(String value) {
            addCriterion("SURRENDER_TIME =", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeNotEqualTo(String value) {
            addCriterion("SURRENDER_TIME <>", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeGreaterThan(String value) {
            addCriterion("SURRENDER_TIME >", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SURRENDER_TIME >=", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeLessThan(String value) {
            addCriterion("SURRENDER_TIME <", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeLessThanOrEqualTo(String value) {
            addCriterion("SURRENDER_TIME <=", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeLike(String value) {
            addCriterion("SURRENDER_TIME like", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeNotLike(String value) {
            addCriterion("SURRENDER_TIME not like", value, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeIn(List<String> values) {
            addCriterion("SURRENDER_TIME in", values, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeNotIn(List<String> values) {
            addCriterion("SURRENDER_TIME not in", values, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeBetween(String value1, String value2) {
            addCriterion("SURRENDER_TIME between", value1, value2, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andSurrenderTimeNotBetween(String value1, String value2) {
            addCriterion("SURRENDER_TIME not between", value1, value2, "surrenderTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeIsNull() {
            addCriterion("EFFECT_START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeIsNotNull() {
            addCriterion("EFFECT_START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeEqualTo(String value) {
            addCriterion("EFFECT_START_TIME =", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeNotEqualTo(String value) {
            addCriterion("EFFECT_START_TIME <>", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeGreaterThan(String value) {
            addCriterion("EFFECT_START_TIME >", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("EFFECT_START_TIME >=", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeLessThan(String value) {
            addCriterion("EFFECT_START_TIME <", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeLessThanOrEqualTo(String value) {
            addCriterion("EFFECT_START_TIME <=", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeLike(String value) {
            addCriterion("EFFECT_START_TIME like", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeNotLike(String value) {
            addCriterion("EFFECT_START_TIME not like", value, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeIn(List<String> values) {
            addCriterion("EFFECT_START_TIME in", values, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeNotIn(List<String> values) {
            addCriterion("EFFECT_START_TIME not in", values, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeBetween(String value1, String value2) {
            addCriterion("EFFECT_START_TIME between", value1, value2, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectStartTimeNotBetween(String value1, String value2) {
            addCriterion("EFFECT_START_TIME not between", value1, value2, "effectStartTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeIsNull() {
            addCriterion("EFFECT_END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeIsNotNull() {
            addCriterion("EFFECT_END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeEqualTo(String value) {
            addCriterion("EFFECT_END_TIME =", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeNotEqualTo(String value) {
            addCriterion("EFFECT_END_TIME <>", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeGreaterThan(String value) {
            addCriterion("EFFECT_END_TIME >", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("EFFECT_END_TIME >=", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeLessThan(String value) {
            addCriterion("EFFECT_END_TIME <", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeLessThanOrEqualTo(String value) {
            addCriterion("EFFECT_END_TIME <=", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeLike(String value) {
            addCriterion("EFFECT_END_TIME like", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeNotLike(String value) {
            addCriterion("EFFECT_END_TIME not like", value, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeIn(List<String> values) {
            addCriterion("EFFECT_END_TIME in", values, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeNotIn(List<String> values) {
            addCriterion("EFFECT_END_TIME not in", values, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeBetween(String value1, String value2) {
            addCriterion("EFFECT_END_TIME between", value1, value2, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andEffectEndTimeNotBetween(String value1, String value2) {
            addCriterion("EFFECT_END_TIME not between", value1, value2, "effectEndTime");
            return (Criteria) this;
        }

        public Criteria andSumInsuredIsNull() {
            addCriterion("SUM_INSURED is null");
            return (Criteria) this;
        }

        public Criteria andSumInsuredIsNotNull() {
            addCriterion("SUM_INSURED is not null");
            return (Criteria) this;
        }

        public Criteria andSumInsuredEqualTo(BigDecimal value) {
            addCriterion("SUM_INSURED =", value, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredNotEqualTo(BigDecimal value) {
            addCriterion("SUM_INSURED <>", value, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredGreaterThan(BigDecimal value) {
            addCriterion("SUM_INSURED >", value, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_INSURED >=", value, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredLessThan(BigDecimal value) {
            addCriterion("SUM_INSURED <", value, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_INSURED <=", value, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredIn(List<BigDecimal> values) {
            addCriterion("SUM_INSURED in", values, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredNotIn(List<BigDecimal> values) {
            addCriterion("SUM_INSURED not in", values, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_INSURED between", value1, value2, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andSumInsuredNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_INSURED not between", value1, value2, "sumInsured");
            return (Criteria) this;
        }

        public Criteria andFeeRateIsNull() {
            addCriterion("FEE_RATE is null");
            return (Criteria) this;
        }

        public Criteria andFeeRateIsNotNull() {
            addCriterion("FEE_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andFeeRateEqualTo(BigDecimal value) {
            addCriterion("FEE_RATE =", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotEqualTo(BigDecimal value) {
            addCriterion("FEE_RATE <>", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateGreaterThan(BigDecimal value) {
            addCriterion("FEE_RATE >", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE_RATE >=", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateLessThan(BigDecimal value) {
            addCriterion("FEE_RATE <", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE_RATE <=", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateIn(List<BigDecimal> values) {
            addCriterion("FEE_RATE in", values, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotIn(List<BigDecimal> values) {
            addCriterion("FEE_RATE not in", values, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE_RATE between", value1, value2, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE_RATE not between", value1, value2, "feeRate");
            return (Criteria) this;
        }

        public Criteria andPremiumIsNull() {
            addCriterion("PREMIUM is null");
            return (Criteria) this;
        }

        public Criteria andPremiumIsNotNull() {
            addCriterion("PREMIUM is not null");
            return (Criteria) this;
        }

        public Criteria andPremiumEqualTo(BigDecimal value) {
            addCriterion("PREMIUM =", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumNotEqualTo(BigDecimal value) {
            addCriterion("PREMIUM <>", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumGreaterThan(BigDecimal value) {
            addCriterion("PREMIUM >", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PREMIUM >=", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumLessThan(BigDecimal value) {
            addCriterion("PREMIUM <", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PREMIUM <=", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumIn(List<BigDecimal> values) {
            addCriterion("PREMIUM in", values, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumNotIn(List<BigDecimal> values) {
            addCriterion("PREMIUM not in", values, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PREMIUM between", value1, value2, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PREMIUM not between", value1, value2, "premium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumIsNull() {
            addCriterion("ACTUAL_PREMIUM is null");
            return (Criteria) this;
        }

        public Criteria andActualPremiumIsNotNull() {
            addCriterion("ACTUAL_PREMIUM is not null");
            return (Criteria) this;
        }

        public Criteria andActualPremiumEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PREMIUM =", value, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumNotEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PREMIUM <>", value, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumGreaterThan(BigDecimal value) {
            addCriterion("ACTUAL_PREMIUM >", value, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PREMIUM >=", value, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumLessThan(BigDecimal value) {
            addCriterion("ACTUAL_PREMIUM <", value, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PREMIUM <=", value, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumIn(List<BigDecimal> values) {
            addCriterion("ACTUAL_PREMIUM in", values, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumNotIn(List<BigDecimal> values) {
            addCriterion("ACTUAL_PREMIUM not in", values, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTUAL_PREMIUM between", value1, value2, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andActualPremiumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTUAL_PREMIUM not between", value1, value2, "actualPremium");
            return (Criteria) this;
        }

        public Criteria andApplyNumIsNull() {
            addCriterion("APPLY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andApplyNumIsNotNull() {
            addCriterion("APPLY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andApplyNumEqualTo(BigDecimal value) {
            addCriterion("APPLY_NUM =", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumNotEqualTo(BigDecimal value) {
            addCriterion("APPLY_NUM <>", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumGreaterThan(BigDecimal value) {
            addCriterion("APPLY_NUM >", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLY_NUM >=", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumLessThan(BigDecimal value) {
            addCriterion("APPLY_NUM <", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLY_NUM <=", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumIn(List<BigDecimal> values) {
            addCriterion("APPLY_NUM in", values, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumNotIn(List<BigDecimal> values) {
            addCriterion("APPLY_NUM not in", values, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLY_NUM between", value1, value2, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLY_NUM not between", value1, value2, "applyNum");
            return (Criteria) this;
        }

        public Criteria andBizDataIsNull() {
            addCriterion("BIZ_DATA is null");
            return (Criteria) this;
        }

        public Criteria andBizDataIsNotNull() {
            addCriterion("BIZ_DATA is not null");
            return (Criteria) this;
        }

        public Criteria andBizDataEqualTo(String value) {
            addCriterion("BIZ_DATA =", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataNotEqualTo(String value) {
            addCriterion("BIZ_DATA <>", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataGreaterThan(String value) {
            addCriterion("BIZ_DATA >", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataGreaterThanOrEqualTo(String value) {
            addCriterion("BIZ_DATA >=", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataLessThan(String value) {
            addCriterion("BIZ_DATA <", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataLessThanOrEqualTo(String value) {
            addCriterion("BIZ_DATA <=", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataLike(String value) {
            addCriterion("BIZ_DATA like", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataNotLike(String value) {
            addCriterion("BIZ_DATA not like", value, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataIn(List<String> values) {
            addCriterion("BIZ_DATA in", values, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataNotIn(List<String> values) {
            addCriterion("BIZ_DATA not in", values, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataBetween(String value1, String value2) {
            addCriterion("BIZ_DATA between", value1, value2, "bizData");
            return (Criteria) this;
        }

        public Criteria andBizDataNotBetween(String value1, String value2) {
            addCriterion("BIZ_DATA not between", value1, value2, "bizData");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeIsNull() {
            addCriterion("SURRENDER_FEE is null");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeIsNotNull() {
            addCriterion("SURRENDER_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeEqualTo(BigDecimal value) {
            addCriterion("SURRENDER_FEE =", value, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeNotEqualTo(BigDecimal value) {
            addCriterion("SURRENDER_FEE <>", value, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeGreaterThan(BigDecimal value) {
            addCriterion("SURRENDER_FEE >", value, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SURRENDER_FEE >=", value, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeLessThan(BigDecimal value) {
            addCriterion("SURRENDER_FEE <", value, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SURRENDER_FEE <=", value, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeIn(List<BigDecimal> values) {
            addCriterion("SURRENDER_FEE in", values, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeNotIn(List<BigDecimal> values) {
            addCriterion("SURRENDER_FEE not in", values, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SURRENDER_FEE between", value1, value2, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andSurrenderFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SURRENDER_FEE not between", value1, value2, "surrenderFee");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNull() {
            addCriterion("PROD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNotNull() {
            addCriterion("PROD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProdTypeEqualTo(String value) {
            addCriterion("PROD_TYPE =", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotEqualTo(String value) {
            addCriterion("PROD_TYPE <>", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThan(String value) {
            addCriterion("PROD_TYPE >", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_TYPE >=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThan(String value) {
            addCriterion("PROD_TYPE <", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThanOrEqualTo(String value) {
            addCriterion("PROD_TYPE <=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLike(String value) {
            addCriterion("PROD_TYPE like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotLike(String value) {
            addCriterion("PROD_TYPE not like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeIn(List<String> values) {
            addCriterion("PROD_TYPE in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotIn(List<String> values) {
            addCriterion("PROD_TYPE not in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeBetween(String value1, String value2) {
            addCriterion("PROD_TYPE between", value1, value2, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotBetween(String value1, String value2) {
            addCriterion("PROD_TYPE not between", value1, value2, "prodType");
            return (Criteria) this;
        }

        public Criteria andInputdateIsNull() {
            addCriterion("INPUTDATE is null");
            return (Criteria) this;
        }

        public Criteria andInputdateIsNotNull() {
            addCriterion("INPUTDATE is not null");
            return (Criteria) this;
        }

        public Criteria andInputdateEqualTo(Date value) {
            addCriterionForJDBCDate("INPUTDATE =", value, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("INPUTDATE <>", value, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateGreaterThan(Date value) {
            addCriterionForJDBCDate("INPUTDATE >", value, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("INPUTDATE >=", value, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateLessThan(Date value) {
            addCriterionForJDBCDate("INPUTDATE <", value, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("INPUTDATE <=", value, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateIn(List<Date> values) {
            addCriterionForJDBCDate("INPUTDATE in", values, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("INPUTDATE not in", values, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("INPUTDATE between", value1, value2, "inputdate");
            return (Criteria) this;
        }

        public Criteria andInputdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("INPUTDATE not between", value1, value2, "inputdate");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumIsNull() {
            addCriterion("PERSON_PHONENUM is null");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumIsNotNull() {
            addCriterion("PERSON_PHONENUM is not null");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumEqualTo(Long value) {
            addCriterion("PERSON_PHONENUM =", value, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumNotEqualTo(Long value) {
            addCriterion("PERSON_PHONENUM <>", value, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumGreaterThan(Long value) {
            addCriterion("PERSON_PHONENUM >", value, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumGreaterThanOrEqualTo(Long value) {
            addCriterion("PERSON_PHONENUM >=", value, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumLessThan(Long value) {
            addCriterion("PERSON_PHONENUM <", value, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumLessThanOrEqualTo(Long value) {
            addCriterion("PERSON_PHONENUM <=", value, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumIn(List<Long> values) {
            addCriterion("PERSON_PHONENUM in", values, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumNotIn(List<Long> values) {
            addCriterion("PERSON_PHONENUM not in", values, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumBetween(Long value1, Long value2) {
            addCriterion("PERSON_PHONENUM between", value1, value2, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andPersonPhonenumNotBetween(Long value1, Long value2) {
            addCriterion("PERSON_PHONENUM not between", value1, value2, "personPhonenum");
            return (Criteria) this;
        }

        public Criteria andDispatchflagIsNull() {
            addCriterion("DISPATCHFLAG is null");
            return (Criteria) this;
        }

        public Criteria andDispatchflagIsNotNull() {
            addCriterion("DISPATCHFLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchflagEqualTo(String value) {
            addCriterion("DISPATCHFLAG =", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagNotEqualTo(String value) {
            addCriterion("DISPATCHFLAG <>", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagGreaterThan(String value) {
            addCriterion("DISPATCHFLAG >", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagGreaterThanOrEqualTo(String value) {
            addCriterion("DISPATCHFLAG >=", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagLessThan(String value) {
            addCriterion("DISPATCHFLAG <", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagLessThanOrEqualTo(String value) {
            addCriterion("DISPATCHFLAG <=", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagLike(String value) {
            addCriterion("DISPATCHFLAG like", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagNotLike(String value) {
            addCriterion("DISPATCHFLAG not like", value, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagIn(List<String> values) {
            addCriterion("DISPATCHFLAG in", values, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagNotIn(List<String> values) {
            addCriterion("DISPATCHFLAG not in", values, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagBetween(String value1, String value2) {
            addCriterion("DISPATCHFLAG between", value1, value2, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andDispatchflagNotBetween(String value1, String value2) {
            addCriterion("DISPATCHFLAG not between", value1, value2, "dispatchflag");
            return (Criteria) this;
        }

        public Criteria andPolicynoIsNull() {
            addCriterion("POLICYNO is null");
            return (Criteria) this;
        }

        public Criteria andPolicynoIsNotNull() {
            addCriterion("POLICYNO is not null");
            return (Criteria) this;
        }

        public Criteria andPolicynoEqualTo(String value) {
            addCriterion("POLICYNO =", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoNotEqualTo(String value) {
            addCriterion("POLICYNO <>", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoGreaterThan(String value) {
            addCriterion("POLICYNO >", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoGreaterThanOrEqualTo(String value) {
            addCriterion("POLICYNO >=", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoLessThan(String value) {
            addCriterion("POLICYNO <", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoLessThanOrEqualTo(String value) {
            addCriterion("POLICYNO <=", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoLike(String value) {
            addCriterion("POLICYNO like", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoNotLike(String value) {
            addCriterion("POLICYNO not like", value, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoIn(List<String> values) {
            addCriterion("POLICYNO in", values, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoNotIn(List<String> values) {
            addCriterion("POLICYNO not in", values, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoBetween(String value1, String value2) {
            addCriterion("POLICYNO between", value1, value2, "policyno");
            return (Criteria) this;
        }

        public Criteria andPolicynoNotBetween(String value1, String value2) {
            addCriterion("POLICYNO not between", value1, value2, "policyno");
            return (Criteria) this;
        }

        public Criteria andGathernoIsNull() {
            addCriterion("GATHERNO is null");
            return (Criteria) this;
        }

        public Criteria andGathernoIsNotNull() {
            addCriterion("GATHERNO is not null");
            return (Criteria) this;
        }

        public Criteria andGathernoEqualTo(String value) {
            addCriterion("GATHERNO =", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoNotEqualTo(String value) {
            addCriterion("GATHERNO <>", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoGreaterThan(String value) {
            addCriterion("GATHERNO >", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoGreaterThanOrEqualTo(String value) {
            addCriterion("GATHERNO >=", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoLessThan(String value) {
            addCriterion("GATHERNO <", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoLessThanOrEqualTo(String value) {
            addCriterion("GATHERNO <=", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoLike(String value) {
            addCriterion("GATHERNO like", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoNotLike(String value) {
            addCriterion("GATHERNO not like", value, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoIn(List<String> values) {
            addCriterion("GATHERNO in", values, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoNotIn(List<String> values) {
            addCriterion("GATHERNO not in", values, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoBetween(String value1, String value2) {
            addCriterion("GATHERNO between", value1, value2, "gatherno");
            return (Criteria) this;
        }

        public Criteria andGathernoNotBetween(String value1, String value2) {
            addCriterion("GATHERNO not between", value1, value2, "gatherno");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}