module.exports = function(grunt){
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),


		sass: {                              // Task
			dist: {                            // Target
				options: {                       // Target options
					style: 'expanded',
					noCache: true
				},
				files: {                         // Dictionary of files
					'css/bootstrap.css':'common/sass/bootstrap.scss',       // 'destination': 'source'
					'css/main.css': 'common/sass/main.scss',
					'css/trace.css':'common/sass/trace.scss'
				}
			}
		},
		watch: {
			script: {
				files: ['common/sass/main.scss','common/sass/trace.scss'],
				tasks: ['sass']
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-sass');

	grunt.registerTask('default', ['sass']);
}