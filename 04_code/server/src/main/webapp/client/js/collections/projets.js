/**
 * @author NG
 */
// Filename: collections/projets
// Pull in the Model module from above
define([
  'underscore',
  'backbone',
  'models/projet'
], function(_, Backbone, ProjetModel){
    
  var ProjetCollection = Backbone.Collection.extend({
    model: ProjetModel,
    url : function(){
        return 'http://localhost:8080/mco-server/rest/projets';
    },
  });
  
  
    
  // You don't usually return a collection instantiated
  return ProjetCollection;
});