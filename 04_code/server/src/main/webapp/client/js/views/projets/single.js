/**
 * @author NG
 */
define(['jquery', 'bootstrap', 'underscore', 'backbone', 'models/projet', 'text!templates/projets/single.html'], function($, Bootstrap, _, Backbone, ProjetModel, singleProjetTemplate) {

    var SingleProjetView = Backbone.View.extend({

        el : '#mco-id-app-left-container',
        
        initialize : function(options){
        	this.model = options.model;
        },

        render : function() {
            console.log('SingleProjetView render...');
            var compiledTemplate;
            compiledTemplate = _.template(singleProjetTemplate, this.model.toJSON());
            $(this.el).html(compiledTemplate);
        }

        /*events : {
            'click #submit' : 'enregistrerProjet'
        },*/

        /*enregistrerProjet : function() {
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
        }*/
    });
    return SingleProjetView;
});
