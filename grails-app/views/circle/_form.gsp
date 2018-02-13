<%@ page import="com.circle.server.Circle" %>



<div class="fieldcontain ${hasErrors(bean: circleInstance, field: 'age', 'error')} required">
	<label for="age">
		<g:message code="circle.age.label" default="Age" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="age" type="number" value="${circleInstance.age}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: circleInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="circle.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${circleInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: circleInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="circle.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${circleInstance?.lastName}"/>
</div>

