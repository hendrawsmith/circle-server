<%@ page import="com.circle.model.Biodata" %>



<div class="fieldcontain ${hasErrors(bean: biodataInstance, field: 'age', 'error')} required">
	<label for="age">
		<g:message code="biodata.age.label" default="Age" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="age" type="number" value="${biodataInstance.age}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: biodataInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="biodata.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${biodataInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: biodataInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="biodata.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${biodataInstance?.lastName}"/>
</div>

