const { series, parallel } = require('gulp');

const gulp = require('gulp');
const sass = require('gulp-sass');
const uglifycss = require('gulp-uglifycss');

var sassPath = './src/main/resources/static/sass/';
var cssPath = './src/main/resources/static/css';

var sassFiles = sassPath.concat('*.scss');
var cssFiles = cssPath.concat('*.css');

function style() {
    return gulp.src(sassFiles)
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest(cssPath));
}

function minifyStyle() {
    return gulp.src(cssFiles)
        .pipe(uglifycss({
          "uglyComments": true
        }))
        .pipe(gulp.dest(cssPath));
}

function watch() {
    gulp.watch(sassFiles, style);
    gulp.watch(cssFiles, minifyStyle);
}

exports.build = series(style, minifyStyle);
exports.default = series(style, minifyStyle, watch);