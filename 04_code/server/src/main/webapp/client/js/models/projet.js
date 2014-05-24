/**
 * @author NG
 */
// Filename: models/projet
define(['underscore', 'backbone'], function(_, Backbone) {

    var ProjetModel = Backbone.Model.extend({

        defaults : {
            nom : 'Mon Projet',
            description : 'Un projet pertinent',
            debut : new Date(),
            fin : new Date(),
            derniereMAJ : 'Non determiné',
            nbMembre : 0,
            nbOutils : 0,
            nbCompetences : 0,
            statut : 0
        },

        urlRoot : function() {
            return 'http://localhost:8080/mco-server/rest/projets/';
        }
    });

    // Return the model for the module
    return ProjetModel;
});
