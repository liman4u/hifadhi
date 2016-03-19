<?php

/****************   Model binding into route **************************/
Route::model('article', 'App\Article');
Route::model('articlecategory', 'App\ArticleCategory');
Route::model('language', 'App\Language');
Route::model('photoalbum', 'App\PhotoAlbum');
Route::model('photo', 'App\Photo');
Route::model('user', 'App\User');
Route::pattern('id', '[0-9]+');
Route::pattern('slug', '[0-9a-z-_]+');

/***************    Site routes  **********************************/
Route::get('/', 'HomeController@index');
Route::get('home', 'HomeController@index');
Route::get('about', 'PagesController@about');
Route::get('contact', 'PagesController@contact');
Route::get('articles', 'ArticlesController@index');
Route::get('article/{slug}', 'ArticlesController@show');
Route::get('video/{id}', 'VideoController@show');
Route::get('photo/{id}', 'PhotoController@show');

Route::controllers([
    'auth' => 'Auth\AuthController',
    'password' => 'Auth\PasswordController',
]);

/***************    Admin routes  **********************************/
Route::group(['prefix' => 'admin', 'middleware' => 'auth'], function() {

    # Admin Dashboard
    Route::get('dashboard', 'Admin\DashboardController@index');

    # Language
    Route::get('language/data', 'Admin\LanguageController@data');
    Route::get('language/{language}/show', 'Admin\LanguageController@show');
    Route::get('language/{language}/edit', 'Admin\LanguageController@edit');
    Route::get('language/{language}/delete', 'Admin\LanguageController@delete');
    Route::resource('language', 'Admin\LanguageController');

    # Article category
    Route::get('articlecategory/data', 'Admin\ArticleCategoriesController@data');
    Route::get('articlecategory/{articlecategory}/show', 'Admin\ArticleCategoriesController@show');
    Route::get('articlecategory/{articlecategory}/edit', 'Admin\ArticleCategoriesController@edit');
    Route::get('articlecategory/{articlecategory}/delete', 'Admin\ArticleCategoriesController@delete');
    Route::get('articlecategory/reorder', 'ArticleCategoriesController@getReorder');
    Route::resource('articlecategory', 'Admin\ArticleCategoriesController');

    # Articles
    Route::get('article/data', 'Admin\ArticleController@data');
    Route::get('article/{article}/show', 'Admin\ArticleController@show');
    Route::get('article/{article}/edit', 'Admin\ArticleController@edit');
    Route::get('article/{article}/delete', 'Admin\ArticleController@delete');
    Route::get('article/reorder', 'Admin\ArticleController@getReorder');
    Route::resource('article', 'Admin\ArticleController');

    # Menu category
    Route::get('menucategory/data', 'Admin\MenuCategoriesController@data');
    Route::get('menucategory/{menucategory}/show', 'Admin\MenuCategoriesController@show');
    Route::get('menucategory/{menucategory}/edit', 'Admin\MenuCategoriesController@edit');
    Route::get('menucategory/{menucategory}/delete', 'Admin\MenuCategoriesController@delete');
    Route::get('menucategory/reorder', 'MenuCategoriesController@getReorder');
    Route::resource('menucategory', 'Admin\MenuCategoriesController');

    # Menu item
    Route::get('menuitem/data', 'Admin\MenuItemController@data');
    Route::get('menuitem/{menuitem}/show', 'Admin\MenuItemController@show');
    Route::get('menuitem/{menuitem}/edit', 'Admin\MenuItemController@edit');
    Route::get('menuitem/{menuitem}/delete', 'Admin\MenuItemController@delete');
    Route::get('menuitem/reorder', 'Admin\MenuItemController@getReorder');
    Route::resource('menuitem', 'Admin\MenuItemController');
    
    # Customer
    Route::get('customer/data', 'Admin\CustomerController@data');
    Route::get('customer/{customer}/delete', 'Admin\CustomerController@delete');
    Route::get('customer/reorder', 'Admin\CustomerController@getReorder');
    Route::resource('customer', 'Admin\CustomerController');
    
    # Order
    Route::get('menuitemorder/data', 'Admin\OrderController@data');
    Route::get('menuitemorder/{order}/delete', 'Admin\OrderController@data');
    Route::get('menuitemorder/reorder', 'Admin\OrderController@data@getReorder');
    Route::resource('menuitemorder', 'Admin\OrderController@data');

    # Photo Album
    Route::get('photoalbum/data', 'Admin\PhotoAlbumController@data');
    Route::get('photoalbum/{photoalbum}/show', 'Admin\PhotoAlbumController@show');
    Route::get('photoalbum/{photoalbum}/edit', 'Admin\PhotoAlbumController@edit');
    Route::get('photoalbum/{photoalbum}/delete', 'Admin\PhotoAlbumController@delete');
    Route::resource('photoalbum', 'Admin\PhotoAlbumController');

    # Photo
    Route::get('photo/data', 'Admin\PhotoController@data');
    Route::get('photo/{photo}/show', 'Admin\PhotoController@show');
    Route::get('photo/{photo}/edit', 'Admin\PhotoController@edit');
    Route::get('photo/{photo}/delete', 'Admin\PhotoController@delete');
    Route::resource('photo', 'Admin\PhotoController');

    # Users
    Route::get('user/data', 'Admin\UserController@data');
    Route::get('user/{user}/show', 'Admin\UserController@show');
    Route::get('user/{user}/edit', 'Admin\UserController@edit');
    Route::get('user/{user}/delete', 'Admin\UserController@delete');
    Route::resource('user', 'Admin\UserController');
    
    
    
});

//APIs
Route::group(['prefix' => 'api'], function() {

    Route::get('markers', 'Api\MobileController@getRestaurants');
    Route::get('markers/search2/{query}', 'Api\MobileController@searchItems');
    
});