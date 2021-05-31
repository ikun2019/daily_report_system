<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報　詳細ページ</h2>

                <table>
                    <tr>
                        <th>氏名</th>
                        <td><c:out value="${report.employee.name}" /></td>
                    </tr>
                    <tr>
                        <th>日付</th>
                        <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                    <tr>
                        <th>内容</th>
                        <td>
                            <pre><c:out value="${report.content}" /></pre>
                        </td>
                    </tr>
                    <tr>
                        <th>登録日時</th>
                        <td><fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                    <tr>
                        <th>更新日時</th>
                        <td><fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </table>
                <!-- いいね機能 -->
                <div class="good_button text-right" style="font-size: 20px;">
                    <c:out value="${likes_count}" />
                    <c:choose>
                        <c:when test="${like >= 1}">
                            <label for="likes_destroy"><i class="fas fa-thumbs-up"></i></label>
                            <form method="POST" name="likes_destroy" action="<c:url value='/likes/destroy' />">
                                <input type="hidden" name="_token" value="${_token}" />
                                <input type="hidden" name="report_id" value="${report.id}" />
                                <input type="submit" id="likes_destroy" style="display:none;" />
                            </form>
                        </c:when>
                        <c:otherwise>
                            <label for="likes_create"><i class="far fa-thumbs-up"></i></label>
                            <form method="POST" name="likes_create" action="<c:url value='/likes/create' />">
                                <input type="hidden" name="_token" value="${_token}" />
                                <input type="hidden" name="report_id" value="${report.id}" />
                                <input type="submit" id="likes_create" style="display:none;" />
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
                <!-- 承認ボタン -->
                <div class="text-center mt-3">
                    <c:choose>
                        <c:when test="${report.approval != null}">
                            <form method="POST" action="<c:url value='/reports/approved' />">
                                <input type="submit" class="btn btn-success" value="承認済">
                                <input type="hidden" name="_token" value="${_token}" />
                                <input type="hidden" name="destroy_report_id" value="${report.id}" />
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="POST" action="<c:url value='/reports/approve' />">
                               <input type="submit" class="btn btn-warning" value="未承認">
                               <input type="hidden" name="_token" value="${_token}" />
                               <input type="hidden" name="create_report_id" value="${report.id}" />
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value='/reports/edit?id=${report.id}' />">この日報を編集する</a></p>
                </c:if>
            </c:when>

            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>