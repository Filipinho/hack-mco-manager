// Filename: main.js

// Require.js allows us to configure shortcut alias
require.config({
  paths: {
    jquery: 'jquery.min',
    bootstrap: 'bootstrap.min',
    underscore: 'underscore',
    backbone: 'backbone.min'
  }
});

// Load our app module and pass it to our definition function
// The "app" dependency is passed in as "McoApp"
require(['mco',], function(McoApp){ 
        McoApp.initialize();
    });