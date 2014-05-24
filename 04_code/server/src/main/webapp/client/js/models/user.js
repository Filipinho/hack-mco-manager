/**
 * @author NG
 */
// Filename: models/user.js
define([
  'underscore',
  'backbone'
], function(_, Backbone){
    
  var UserModel = Backbone.Model.extend({
      
    defaults: {
      email: "sgmolu@gmail.com",
      sesame:"mco",
      isCP:true
    }
    
  });
  
  // Return the model for the module
  return UserModel;
});