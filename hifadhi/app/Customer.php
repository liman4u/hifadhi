<?php

namespace App;

use Cviebrock\EloquentSluggable\SluggableInterface;
use Cviebrock\EloquentSluggable\SluggableTrait;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Customer extends Model implements SluggableInterface {

	use SoftDeletes;
	use SluggableTrait;

	protected $dates = ['deleted_at'];


	protected $guarded  = array('id');

	/**
	 * Get the customer's restaurant.
	 *
	 * @return Restaurant
	 */
	public function restaurant()
	{
		return $this->belongsTo('App\Restaurant');
	}

}
