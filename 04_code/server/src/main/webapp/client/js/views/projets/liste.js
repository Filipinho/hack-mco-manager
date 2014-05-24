// Filename: views/projets/liste
//Using the Require.js text! plugin, we are loaded raw text
// which will be used as our views primary template
define(['jquery', 
'bootstrap', 
'underscore', 
'backbone', 
'text!templates/communs/layout.html', 
'text!templates/projets/liste.html',
'views/projets/single',
'collections/projets'], function($, Bootstrap, _, Backbone, layoutTemplate, listeProjetTemplate, SingleProjetView, ProjetCollection ) {
    
    var ProjectListView = Backbone.View.extend({

        el: $('body'),
        
        initialize : function(options) {

            this.options = _.defaults(options, this.defaultOptions);

            _.bindAll(this, 'render', 'creerUnProjet', 'importerDesProjets');
            
            this.collection = new ProjetCollection();
            this.collection.on('add',this.render,this);
            this.collection.fetch();
            //this.events = options.events;
            //this.el = options.el;
            this.render();
        },
        
        events:{
            'click #mco-btn-creer-projet'   : 'creerUnProjet',
            'click mco-btn-importer-projet' : 'importerDesProjets',
            'click #id-projet-1' : 'afficherDetail',
        },
        
        render : function() {
            // Using Underscore we can compile our template with data
            var compiledTemplate, listeProjetCompiled, dataEmpty, dataFilled;
            console.log('listeView');
            dataEmpty = {
                nbProjet : 0,
                message : 'Une fois crée, vos projets apparaîtront ici'
            };

            dataFilled = {
                nbProjet : this.collection.size(),
                projets : this.collection.toJSON(),
                message : 'projets'
            };

            compiledTemplate = _.template(layoutTemplate, dataFilled);
            
            // Append our compiled template to this Views "el"
            this.$el.html(compiledTemplate);
            
            //this.collection.each(this.afficherUnProjet, this);
        },
        
        afficherUnProjet : function(projet, projets, options){
            
            unProjet = new SingleProjetView({
            model : projet, 
            //el : domElt,
            //events : eventObject,
            });
            unProjet.render();
        },
        
        creerUnProjet:function(){
            console.log('click sur creer un projet');
            Backbone.history.navigate('creerProjet', {
                trigger : true
            });
        },
        
        afficherDetail : function(){
        	 console.log('click sur projet 1');
        	 Backbone.history.navigate('voirProjet/1', {
                 trigger : true
             });
        },
        
        importerDesProjets:function(){
            console.log('click sur importerDesProjets');
            Backbone.history.navigate('importerProjets', {
                trigger : true
            });
        }
    });
    
    
    // Our module now returns our view
    return ProjectListView;
}); 