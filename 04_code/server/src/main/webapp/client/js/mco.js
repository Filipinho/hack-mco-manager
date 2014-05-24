// Filename: mco.js
define([
  'jquery',
  'bootstrap',
  'underscore',
  'backbone',
  'router', // Request router.js
], function($, Bootstrap, _, Backbone, Router){
  // Pass in our Router module and call it's initialize function
  var initialize = function(){
    Router.initialize();
  };

  return {
    initialize: initialize
  };
});