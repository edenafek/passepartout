'use strict';

/**
 * @ngdoc function
 * @name passepartoutApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the passepartoutApp
 */
angular.module('passepartoutApp')
.controller('GameCtrl', function ($scope, $http, $Questions, $User, $location,$timeout) {

	$scope.currentQuestion = 0;
	$scope.lives = 3;
	$scope.score = 0;
	$scope.guessed=-1;


//	$scope.init = function() {
//		//$http(localhost:8080/rest/question)
//		$Questions.getQuestions();
//		$scope.questions = $Questions.questions;
//
//	}

	
	$scope.questions=$Questions.questions;
	
	
	$scope.getAnswerText = function(text) {

		if ($scope.endsWith(text, ".svg")) {
			return "<img src='" + text.replace(" ", "_") + "' />";
		}
		return text;
	}
	
	$scope.endsWith= function (str, suffix) {
	    return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}
	
	$scope.getAnswerClass = function(index) {
		if ($scope.guessed != -1 && index==$scope.questions[$scope.currentQuestion].answerIndex) {
			return "green"
		}
		else if ($scope.guessed==index) {
			return "red";
		}
		return "";
	}
	$scope.guessAnswer = function(index) {
		$scope.guessed=index;

		if (index == $scope.questions[$scope.currentQuestion].answerIndex) { //yes!
			setTimeout(function(){$scope.currentQuestion += 1; $scope.guessed=-1;$scope.$apply();}, 3000);
			
			
			
			$scope.score+=$scope.questions[$scope.currentQuestion].score;
		}
		else { //no!
			
			setTimeout(function(){
				$scope.lives -= 1;
				if ($scope.lives>0) {
					$scope.currentQuestion += 1;
					}
				$scope.guessed=-1;
				$scope.$apply()

				}, 3000);
			
	}
	}
	
	
	$scope.submitScore = function() {
		$User.setHighscore($scope.score)
		//go to high score page
	}
	
	$scope.startOver= function() {
		$scope.loading = true;
		$Questions.getQuestions($User.id, function() {
			console.log("loaded questions");
			$scope.loading = false;
			$scope.questions=$Questions.questions;
			$scope.currentQuestion = 0;
			$scope.lives = 3;
			$scope.score = 0;
		});

	}
	
	$scope.endGame = function() {
		$scope.currentQuestion = 0;
		$scope.lives = 3;
		$scope.score=0;
		$location.path('/');
	}


});
