<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>日報管理システムへようこそ</h2>

        <!-- タブメニュー -->
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a href="#tab1" class="nav-link active" data-toggle="tab">【自分の日報　一覧】</a>
            </li>
            <li class="nav-item">
                <a href="#tab2" class="nav-link" data-toggle="tab">【部下の日報一覧】</a>
            </li>
        </ul>

        <!-- １つ目のタブ -->
        <div class="tab-content">
            <div class="tab-pane active" id="tab1">
                <div class="p-3">
                    <table id="report_list">
                        <tbody>
                            <tr>
                                <th class="report_name">氏名</th>
                                <th class="report_date">日付</th>
                                <th class="report_title">タイトル</th>
                                <th class="report_action">操作</th>
                            </tr>
                            <c:forEach var="report" items="${reports}" varStatus="status">
                                <tr class="row${status.count % 2}">
                                    <td class="report_name"><c:out value="${report.employee.name}" /></td>
                                    <td class="report_date"><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                                    <td class="report_title">${report.title}</td>
                                    <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                      <div id="pagination">
                        (全 ${reports_count} 件)<br />
                        <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <c:out value="${i}" />&nbsp;
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- 2つ目のタブ -->
            <c:if test="${login_employee.position > 1}">
                <div class="tab-pane" id="tab2">
                    <div class="p-3">
                        <table id="report_list">
                            <tbody>
                                <tr>
                                    <th class="report_name">氏名</th>
                                    <th class="report_date">日付</th>
                                    <th class="report_title">タイトル</th>
                                    <th class="report_action">操作</th>
                                </tr>
                                <c:forEach var="subordinateReport" items="${subordinatesReports}" varStatus="status">
                                    <tr class="row${status.count % 2}">
                                        <td class="report_name"><c:out value="${subordinateReport.employee.name}" /></td>
                                        <td class="report_date"><c:out value="${subordinateReport.report_date}" /></td>
                                        <td class="report_title"><c:out value="${subordinateReport.title}" /></td>
                                        <td class="report_action"><a href="<c:url value='/reports/show?id=${subordinateReport.id}' />">詳細を見る</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>





        <p><a href="<c:url value='/reports/new' />">新規日報の登録</a></p>
    </c:param>
</c:import>