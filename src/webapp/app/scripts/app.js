'use strict';

/**
 * @ngdoc overview
 * @name passepartoutApp
 * @description
 * # passepartoutApp
 *
 * Main module of the application.
 */
angular
  .module('passepartoutApp', [
    'passepartoutApp.questions',
    'passepartoutApp.user',
    'passepartoutApp.highscores',
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/game', {
        templateUrl: 'views/game.html',
        controller: 'GameCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
