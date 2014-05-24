define(['jquery', 'bootstrap', 'underscore', 'backbone', 'models/user', 'text!templates/communs/login.html'], function($, Bootstrap, _, Backbone, UserModel, loginTemplate) {

    var LoginForm = Backbone.View.extend({

        el : 'body',

        render : function() {
            console.log('loginForm render...');
            $(this.el).html(loginTemplate);
        },

        /*events : {
            'click #submit' : 'seConnecter'
        },*/

        seConnecter : function() {
            console.log('seConnecter...');
            var that = this, user;
            user = new UserModel();
            Backbone.history.navigate('projets', {
                trigger : true
            });
            /*user.save({ message: $('.message').val()}, {
             success: function () {
             that.trigger('postMessage');
             }
             });*/
        }
    });
    return LoginForm;
});
