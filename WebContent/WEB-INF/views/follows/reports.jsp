<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>${follower.name}さんの日報一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="followerReport" items="${followerReports}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_date"><fmt:formatDate value='${followerReport.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${followerReport.title}</td>
                        <td class="report_action"><a href="<c:url value='/reports/show?id=${followerReport.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:param>
</c:import>