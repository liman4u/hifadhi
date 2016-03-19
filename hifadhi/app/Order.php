<?php

namespace App;

use Cviebrock\EloquentSluggable\SluggableInterface;
use Cviebrock\EloquentSluggable\SluggableTrait;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Order extends Model implements SluggableInterface {

	use SoftDeletes;
	use SluggableTrait;

	protected $dates = ['deleted_at'];


	protected $guarded  = array('id');

	/**
	 * Get the customer's order.
	 *
	 * @return Customer
	 */
	public function customer()
	{
		return $this->belongsTo('App\Customer');
	}
        
        /**
	 * Get the menu item.
	 *
	 * @return Menu Item
	 */
	public function menuitem()
	{
		return $this->belongsTo('App\MenuItem');
	}

}
