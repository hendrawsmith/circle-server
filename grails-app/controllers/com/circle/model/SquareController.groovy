package com.circle.model

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = false)
class SquareController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", simpan: "POST"]
	
	def search() {
		Square square = Square.findByName(params.name)
		if (square == null)
			square = new Square() 
		render square as JSON
	} 
	
	def simpan() {
		def req = request.JSON
		Square squareInstance = new Square(name: req.name, color: req.color)
		
		def status = squareInstance.save(flush:true)
		
		def res = ["status":  status]
		render res as JSON
	}
    
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Square.list(params), model:[squareInstanceCount: Square.count()]
    }

    def show(Square squareInstance) {
        respond squareInstance
    }

    def create() {
        respond new Square(params)
    }

    @Transactional
    def save(Square squareInstance) {
        if (squareInstance == null) {
            notFound()
            return
        }

        if (squareInstance.hasErrors()) {
            respond squareInstance.errors, view:'create'
            return
        }

        squareInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'squareInstance.label', default: 'Square'), squareInstance.id])
                redirect squareInstance
            }
            '*' { respond squareInstance, [status: CREATED] }
        }
    }

    def edit(Square squareInstance) {
        respond squareInstance
    }

    @Transactional
    def update(Square squareInstance) {
        if (squareInstance == null) {
            notFound()
            return
        }

        if (squareInstance.hasErrors()) {
            respond squareInstance.errors, view:'edit'
            return
        }

        squareInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Square.label', default: 'Square'), squareInstance.id])
                redirect squareInstance
            }
            '*'{ respond squareInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Square squareInstance) {

        if (squareInstance == null) {
            notFound()
            return
        }

        squareInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Square.label', default: 'Square'), squareInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'squareInstance.label', default: 'Square'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
