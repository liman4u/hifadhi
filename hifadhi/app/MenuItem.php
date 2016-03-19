<?php

namespace App;

use Cviebrock\EloquentSluggable\SluggableInterface;
use Cviebrock\EloquentSluggable\SluggableTrait;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class MenuItem extends Model implements SluggableInterface {

	use SoftDeletes;
	use SluggableTrait;

	protected $dates = ['deleted_at'];


	protected $guarded  = array('id');

	/**
	 * Get the post's category.
	 *
	 * @return MenuCategory
	 */
	public function category()
	{
		return $this->belongsTo('App\MenuCategory');
	}

}
