<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="../include/head.jspf" %>
<body>
<div class="container">
    <%@ include file="../include/menu.jspf" %>

    <form class="form-horizontal" method="post" command="do?command=Profile">

        <fieldset>

            <br>

            <div class="row justify-content-left align-items-start">
                <div class="col-md-12">

                    <div class="row">
                        <div class="col-md-6 text-left">
                            <div class="form-group">
                                <div class="col-md-auto">
                                    <legend style="font-size: 18px;">
                                        <fmt:message key="message.profile"/>
                                    </legend>
                                </div>
                            </div>
                        </div>


                        <div class="col-md-6 text-right">
                            <div class="form-group">
                                <div class="col-md-auto">
                                    <button data-toggle-id="delete-account" name="delete-account" type="button"
                                            data-toggle="modal" data-target="#exampleModalCenter"
                                            class="btn btn-white" style="font-size: 14px;">
                                        <fmt:message key="message.deleteAccount"/>
                                    </button>
                                    <button id="logout" name="logout" class="btn btn-dark" style="font-size: 14px;">
                                        <fmt:message key="message.logout"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle"><fmt:message key="message.deleteAccountHeader"/></h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p><fmt:message key="message.areYouSure"/></p>
                                </div>
                                <div class="modal-footer">
                                    <button id="deletemyaccount" name="deletemyaccount"
                                            class="btn btn-white" style="font-size: 14px;">
                                        <fmt:message key="message.deleteAccount"/>
                                    </button>
                                    <button id="cancel-delete" name="cancel-delete" data-dismiss="modal"
                                            class="btn btn-dark" style="font-size: 14px;">
                                        <fmt:message key="message.cancel"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="row justify-content-left align-items-start">

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-auto control-label" for="username" style="font-size: 16px;">
                                    <fmt:message key="message.login"/>
                                </label>
                                <div class="col-md-auto">
                                    <input id="username" value="${username}" name="username"
                                           minlength="4" maxlength="15" pattern="[[A-Za-z._-]+]{4,15}"
                                           style="font-size: 16px;" type="text" class="form-control input-md"
                                           required="" readonly/>

                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-auto control-label" for="email" style="font-size: 16px;">
                                    <fmt:message key="message.email"/>
                                </label>
                                <div class="col-md-auto">
                                    <input id="email" value="${email}" name="email" type="text" placeholder="e-mail"
                                           minlength="6" maxlength="50" pattern="([\w\.\w]+)@(\w+\.)([a-z]{2,4})"
                                           style="font-size: 16px;" class="form-control input-md" required=""/>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row justify-content-left align-items-start">
                        <!-- Password input-->
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-auto control-label" for="password" style="font-size: 16px;">
                                    <fmt:message key="message.password"/>
                                </label>
                                <div class="col-md-auto">
                                    <input id="password" value="${password}" name="password" type="password"
                                           minlength="5" pattern="[\w]{5,200}" placeholder="password"
                                           class="form-control input-md" required="" style="font-size: 16px;"/>
                                    <span class="help-block" style="font-size: 12px; color: #949494;">
                                        <fmt:message key="message.passwordDescription"/>
                                    </span>
                                </div>
                            </div>
                        </div>


                        <!-- Text input-->

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-auto control-label" for="firstName" style="font-size: 16px;">
                                    <fmt:message key="message.firstName"/>
                                </label>
                                <div class="col-md-auto">
                                    <input id="firstName" value="${firstName}" name="firstName" type="text"
                                           minlength="2" maxlength="15" pattern="[[A-Za-zА-Яа-яЁё-]+]{2,15}"
                                           placeholder="first name" class="form-control input-md" required=""
                                           style="font-size: 16px;"/>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row justify-content-left align-items-start">
                        <!-- Text input-->
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-auto control-label" for="lastName" style="font-size: 16px;">
                                    <fmt:message key="message.lastName"/>
                                </label>
                                <div class="col-md-auto">
                                    <input id="lastName" value="${lastName}" name="lastName" type="text"
                                           minlength="2" maxlength="15" pattern="[[A-Za-zА-Яа-яЁё-]+]{2,15}"
                                           placeholder="last name" class="form-control input-md" required=""
                                           style="font-size: 16px;"/>

                                </div>
                            </div>
                        </div>


                        <!-- Text input-->

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-auto control-label" for="middleName" style="font-size: 16px;">
                                    <fmt:message key="message.middleName"/>
                                </label>
                                <div class="col-md-auto">
                                    <input id="middleName" value="${middleName}" name="middleName" type="text"
                                           maxlength="15" pattern="[[A-Za-zА-Яа-яЁё.-]+]{0,15}"
                                           placeholder="" class="form-control input-md" style="font-size: 16px;"/>

                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- Button (Double) -->
                    <div class="form-group">
                        <div class="col-md-auto text-right">
                            <button id="saveinfo" name="saveinfo" class="btn btn-dark" style="font-size: 14px;">
                                <fmt:message key="message.save"/>
                            </button>
                        </div>
                    </div>
                </div>


            </div>
        </fieldset>
    </form>
</div>
</body>
<footer>
    <div class="container">
        <div class="row justify-content-left align-items-start">
            <div class="col-md-12">
                <%@ include file="../include/footer.jspf" %>
            </div>
        </div>
    </div>
</footer>
</html>


