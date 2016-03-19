<?php

namespace App\Http\Controllers\Admin;

use App\MenuCategory;
use App\Http\Controllers\AdminController;
use App\Http\Requests\Admin\MenuCategoryRequest;
use App\Http\Requests\Admin\DeleteRequest;
use App\Http\Requests\Admin\ReorderRequest;
use Illuminate\Support\Facades\Auth;
use Datatables;

class MenuCategoriesController extends AdminController {

    public function __construct()
    {
        view()->share('type', 'menucategory');
    }
    /**
	 * Display a listing of the resource.
	 *
	 * @return Response
	 */
	public function index()
	{
        return view('admin.menucategory.index');
	}

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create()
    {
        return view('admin.menucategory.create_edit');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @return Response
     */
    public function store(MenuCategoryRequest $request)
    {
        $menucategory = new MenuCategory($request->all());
        $menucategory -> save();
    }
    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function edit(MenuCategory $menucategory)
    {
        return view('admin.menucategory.create_edit',compact('menucategory'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function update(MenuCategoryRequest $request, MenuCategory $menucategory)
    {
        $menucategory -> update($request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */

    public function delete(MenuCategory $menucategory)
    {
        return view('admin.menucategory.delete', compact('menucategory'));
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */
    public function destroy(MenuCategory $menuCategory)
    {
        $menuCategory->delete();
    }

    /**
     * Show a list of all the languages posts formatted for Datatables.
     *
     * @return Datatables JSON
     */
    public function data()
    {
        $menu_categories = MenuCategory::select(array('menu_categories.id','menu_categories.name', 'menu_categories.description', 'menu_categories.created_at'))
            ->orderBy('menu_categories.id', 'DESC')->get();
       
        //var_dump($menu_categories) ;

        return Datatables::of($menu_categories)
           ->add_column('actions', '<a href="{{{ URL::to(\'admin/menucategory/\' . $id . \'/edit\' ) }}}" class="btn btn-success btn-sm iframe" ><span class="glyphicon glyphicon-pencil"></span>  {{ trans("admin/modal.edit") }}</a>
                <a href="{{{ URL::to(\'admin/menucategory/\' . $id . \'/delete\' ) }}}" class="btn btn-sm btn-danger iframe"><span class="glyphicon glyphicon-trash"></span> {{ trans("admin/modal.delete") }}</a>
                <input type="hidden" name="row" value="{{$id}}" id="row">')
            ->remove_column('id')

            ->make();
    }

    /**
     * Reorder items
     *
     * @param items list
     * @return items from @param
     */
    public function getReorder(ReorderRequest $request) {
        $list = $request->list;
        $items = explode(",", $list);
        $order = 1;
        foreach ($items as $value) {
            if ($value != '') {
                MenuCategory::where('id', '=', $value) -> update(array('position' => $order));
                $order++;
            }
        }
        return $list;
    }

}
