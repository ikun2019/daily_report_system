<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h3>【フォロー済み従業員の日報一覧】</h3>
        <table id="report_list">
            <tr>
                <th class="report_name">氏名</th>
                <th class="report_date">日付</th>
                <th class="report_title">タイトル</th>
                <th class="report_action">操作</th>
            </tr>

            <c:forEach var="followed_employee_report" items="${followed_employee_reports}" varStatus="status">
                <tr class="row${status.count % 2}">
                    <td>${followed_employee_report.employee.name}</td>
                    <td>${followed_employee_report.report_date}</td>
                    <td>${followed_employee_report.title}</td>
                    <td><a href="<c:url value='/reports/show?id=${followed_employee_report.id}' />">詳細を見る</a></td>
                </tr>
            </c:forEach>
        </table>

        <div id="pagination">
            (全 ${reports_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/follows/report_lists?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

    </c:param>
</c:import>