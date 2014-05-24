// Filename: router.js
define([
  'jquery',
  'bootstrap',
  'underscore',
  'backbone',
  'views/communs/login',
  'views/projets/liste',
  'views/projets/ajouter',
  'views/projets/single',
  'models/projet'
], function($, Bootstrap, _, Backbone, LoginForm, ProjetListView,AjouterProjetForm,SingleProjetView, ProjetModel){
    // Define some URL routes
  var AppRouter = Backbone.Router.extend({
    routes: {
      '': 'afficherTousLesProjets',
      //'layout.html': 'afficherTousLesProjets',
      'creerProjet':'creerUnNouveauProjet',
      'importerProjets':'importerDepuisUnFichier',
      'voirProjet/:id':'voirDetailProjet',
      '*actions': 'afficherPageInconnue'
    }
  });

  var initialize = function(){
    var app_router = new AppRouter;
    // Call render on the module we loaded in via the dependency array 'views/projects/list'
    app_router.on('route:afficherTousLesProjets', function(){
      console.log('afficherTousLesProjets');
      var projetListView = new ProjetListView();
      projetListView.render();
    });
    
    // As above, call render on our loaded module 'views/users/list'
    app_router.on('route:afficherLogin', function(){
      console.log('afficherLogin');
      var loginForm = new LoginForm();
      loginForm.render();
    });
    
    app_router.on('route:afficherPageInconnue', function(actions){
      // We have no matching route, lets just log what the URL was
      console.log('afficherPageInconnue : ', actions);
    });
    
    app_router.on('route:creerUnNouveauProjet', function(){
      // We have no matching route, lets just log what the URL was
      console.log('click sur creerUnNouveauProjet : ');
      var ajouterProjetForm = new AjouterProjetForm();
      ajouterProjetForm.render();
    });
    
    app_router.on('route:importerDepuisUnFichier', function(){
      // We have no matching route, lets just log what the URL was
      console.log('click sur importerDepuisUnFichier : ');
      var importerProjetForm = new ImporterProjetForm();
      importerProjetForm.render();
    });
    
    app_router.on('route:voirDetailProjet', function(id){
        // We have no matching route, lets just log what the URL was
        console.log('click sur importerDepuisUnFichier : ');
        projet = new ProjetModel({id:id});
        projet.fetch();
        var singleView = new SingleProjetView({model:projet});
        singleView.render();
      });
    //
    Backbone.history.start();
  };
  
  return {
    initialize: initialize
  };
  
});