/**
 * @author NG
 */
define(['jquery', 'bootstrap', 'underscore', 'backbone', 'models/projet', 'text!templates/projets/addProject.html'], function($, Bootstrap, _, Backbone, ProjetModel, addProjetTemplate) {

    var AjouterProjetForm = Backbone.View.extend({

        el : '#app-content-main',

        render : function() {
            console.log('AjouterProjetForm render...');
            $(this.el).html(addProjetTemplate);
        },

        events : {
            'click #submit' : 'enregistrerProjet'
        },

        enregistrerProjet : function() {
            console.log('enregistrerProjet...');
            var that = this, user;
            
            projet = new ProjetModel({
                nom : $('#nom').val(),
                description : $('#description').val(),
                debut:$('#debut').val(),
                fin:$('#fin').val()
            });
            
            projet.save({
                success : function() {
                    that.trigger('postProject');
                    console.log('postProjet and navigate to all projects');
                    Backbone.history.navigate('projets', {trigger : true });
                },
                error:function(){
                    console.error('something happenned!!');
                }
            });
        }
    });
    return AjouterProjetForm;
});
