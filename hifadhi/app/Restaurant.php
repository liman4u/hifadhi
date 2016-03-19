<?php

namespace App;

use Cviebrock\EloquentSluggable\SluggableInterface;
use Cviebrock\EloquentSluggable\SluggableTrait;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Restaurant extends Model implements SluggableInterface {

	use SoftDeletes;
	use SluggableTrait;

	protected $dates = ['deleted_at'];


	protected $guarded  = array('id');

	/**
	 * Get the restaurant user.
	 *
	 * @return User
	 */
	public function user()
	{
		return $this->belongsTo('App\User');
	}

}
