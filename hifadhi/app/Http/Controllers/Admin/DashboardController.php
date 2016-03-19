<?php namespace App\Http\Controllers\Admin;

use App\Http\Controllers\AdminController;
use App\Menuitem;
use App\MenuCategory;
use App\Customer;
use App\Order;
use App\PhotoAlbum;

class DashboardController extends AdminController {

    public function __construct()
    {
        parent::__construct();
        view()->share('type', '');
    }

	public function index()
	{
        $title = "Dashboard";

        $menuitem = MenuItem::count();
        $menucategory = MenuCategory::count();
        $customers = Customer::count();
        $orders = Order::count();
		return view('admin.dashboard.index',  compact('title','menuitem','menucategory','orders','customers'));
	}
}