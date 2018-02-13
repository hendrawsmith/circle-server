<%@ page import="com.circle.model.Square" %>



<div class="fieldcontain ${hasErrors(bean: squareInstance, field: 'color', 'error')} ">
	<label for="color">
		<g:message code="square.color.label" default="Color" />
		
	</label>
	<g:textField name="color" value="${squareInstance?.color}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: squareInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="square.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${squareInstance?.name}"/>
</div>

