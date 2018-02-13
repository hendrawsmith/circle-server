package com.circle.server


import grails.converters.JSON 

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CircleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def login() {
		System.out.println(params)
		Circle circle = Circle.findByFirstName(params.name)
		if (circle == null)
			circle = new Circle() 
		render circle as JSON
	}
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Circle.list(params), model:[circleInstanceCount: Circle.count()]
    }

    def show(Circle circleInstance) {
        respond circleInstance
    }

    def create() {
        respond new Circle(params)
    }

    @Transactional
    def save(Circle circleInstance) {
        if (circleInstance == null) {
            notFound()
            return
        }

        if (circleInstance.hasErrors()) {
            respond circleInstance.errors, view:'create'
            return
        }

        circleInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'circleInstance.label', default: 'Circle'), circleInstance.id])
                redirect circleInstance
            }
            '*' { respond circleInstance, [status: CREATED] }
        }
    }

    def edit(Circle circleInstance) {
        respond circleInstance
    }

    @Transactional
    def update(Circle circleInstance) {
        if (circleInstance == null) {
            notFound()
            return
        }

        if (circleInstance.hasErrors()) {
            respond circleInstance.errors, view:'edit'
            return
        }

        circleInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Circle.label', default: 'Circle'), circleInstance.id])
                redirect circleInstance
            }
            '*'{ respond circleInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Circle circleInstance) {

        if (circleInstance == null) {
            notFound()
            return
        }

        circleInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Circle.label', default: 'Circle'), circleInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'circleInstance.label', default: 'Circle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
