package com.circle.model



import grails.test.mixin.*
import spock.lang.*

@TestFor(SquareController)
@Mock(Square)
class SquareControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.squareInstanceList
            model.squareInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.squareInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def square = new Square()
            square.validate()
            controller.save(square)

        then:"The create view is rendered again with the correct model"
            model.squareInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            square = new Square(params)

            controller.save(square)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/square/show/1'
            controller.flash.message != null
            Square.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def square = new Square(params)
            controller.show(square)

        then:"A model is populated containing the domain instance"
            model.squareInstance == square
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def square = new Square(params)
            controller.edit(square)

        then:"A model is populated containing the domain instance"
            model.squareInstance == square
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/square/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def square = new Square()
            square.validate()
            controller.update(square)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.squareInstance == square

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            square = new Square(params).save(flush: true)
            controller.update(square)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/square/show/$square.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/square/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def square = new Square(params).save(flush: true)

        then:"It exists"
            Square.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(square)

        then:"The instance is deleted"
            Square.count() == 0
            response.redirectedUrl == '/square/index'
            flash.message != null
    }
}
