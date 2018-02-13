package com.circle.model
import grails.converters.JSON



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BiodataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*def search() {
        Biodata biodata = Biodata.findby(params.FirstName)
        if (biodata == null)
            biodata = new Biodata() 
        render biodata as JSON
    } */
       def search() {
        System.out.println(params)
        Biodata biodata = Biodata.findByFirstName(params.FirstName)
        if (biodata == null)
            biodata = new Biodata() 
        render biodata as JSON
    }

    def simpan() {
        def req = request.JSON
        Biodata biodataInstance = new Biodata(FirstName: req.FirstName, LastName: req.LastName, Age: req.Age)
        
        def status = biodataInstance.save(flush:true)
        
        def res = ["status":  status]
        render res as JSON
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Biodata.list(params), model:[biodataInstanceCount: Biodata.count()]
    }

    def show(Biodata biodataInstance) {
        respond biodataInstance
    }

    def create() {
        respond new Biodata(params)
    }

    @Transactional
    def save(Biodata biodataInstance) {
        if (biodataInstance == null) {
            notFound()
            return
        }

        if (biodataInstance.hasErrors()) {
            respond biodataInstance.errors, view:'create'
            return
        }

        biodataInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'biodataInstance.label', default: 'Biodata'), biodataInstance.id])
                redirect biodataInstance
            }
            '*' { respond biodataInstance, [status: CREATED] }
        }
    }

    def edit(Biodata biodataInstance) {
        respond biodataInstance
    }

    @Transactional
    def update(Biodata biodataInstance) {
        if (biodataInstance == null) {
            notFound()
            return
        }

        if (biodataInstance.hasErrors()) {
            respond biodataInstance.errors, view:'edit'
            return
        }

        biodataInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Biodata.label', default: 'Biodata'), biodataInstance.id])
                redirect biodataInstance
            }
            '*'{ respond biodataInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Biodata biodataInstance) {

        if (biodataInstance == null) {
            notFound()
            return
        }

        biodataInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Biodata.label', default: 'Biodata'), biodataInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'biodataInstance.label', default: 'Biodata'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
