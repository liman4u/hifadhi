<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\AdminController;
use App\MenuItem;
use App\MenuCategory;
use App\Language;
use Illuminate\Support\Facades\Input;
use App\Http\Requests\Admin\MenuRequest;
use Illuminate\Support\Facades\Auth;
use Datatables;

class MenuItemController extends AdminController {

    public function __construct()
    {
        view()->share('type', 'menuitem');
    }
     /*
    * Display a listing of the resource.
    *
    * @return Response
    */
    public function index()
    {
        // Show the page
        return view('admin.menuitem.index');
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create() {
        $menucategories = MenuCategory::lists('name', 'id')->toArray();
        return view('admin.menuitem.create_edit', compact( 'menucategories'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @return Response
     */
    public function store(MenuItemRequest $request)
    {
        $menuitem = new Menuitem($request->except('image'));
        $menuitem -> user_id = Auth::id();

        $picture = "";
        if(Input::hasFile('image'))
        {
            $file = Input::file('image');
            $filename = $file->getClientOriginalName();
            $extension = $file -> getClientOriginalExtension();
            $picture = sha1($filename . time()) . '.' . $extension;
        }
        $menuitem -> picture = $picture;
        $menuitem -> save();

        if(Input::hasFile('image'))
        {
            $destinationPath = public_path() . '/images/menuitem/'.$menuitem->id.'/';
            Input::file('image')->move($destinationPath, $picture);
        }
    }
    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function edit(Menu $menuitem)
    {
        $languages = Language::lists('name', 'id')->toArray();
        $menucategories = MenuCategory::lists('title', 'id')->toArray();
        return view('admin.menuitem.create_edit',compact('menuitem','languages','menucategories'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function update(MenuItemRequest $request, MenuItem $menuitem)
    {
        $menu -> user_id = Auth::id();
        $picture = "";
        if(Input::hasFile('image'))
        {
            $file = Input::file('image');
            $filename = $file->getClientOriginalName();
            $extension = $file -> getClientOriginalExtension();
            $picture = sha1($filename . time()) . '.' . $extension;
        }
        $menu -> picture = $picture;
        $menu -> update($request->except('image'));

        if(Input::hasFile('image'))
        {
            $destinationPath = public_path() . '/images/menuitem/'.$menuitem->id.'/';
            Input::file('image')->move($destinationPath, $picture);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */

    public function delete(Menuitem $menuitem)
    {
        return view('admin.menuitem.delete', compact('menuitem'));
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */
    public function destroy(Menuitem $menuitem)
    {
        $menuitem->delete();
    }
    
    /**
     * Show a list of all the languages posts formatted for Datatables.
     *
     * @return Datatables JSON
     */
    public function data()
    {
        $menuitems = Menuitem::join('menu_categories', 'menu_categories.id', '=', 'menu_items.menu_category_id')
            ->select(array('menu_items.id','menu_items.name','menu_items.price','menu_categories.name as category', 
                'menu_items.created_at'))->orderBy('menu_items.id', 'DESC')->get();
       

        return Datatables::of($menuitems)
           ->add_column('actions', '<a href="{{{ URL::to(\'admin/menuitem/\' . $id . \'/edit\' ) }}}" class="btn btn-success btn-sm iframe" ><span class="glyphicon glyphicon-pencil"></span>  {{ trans("admin/modal.edit") }}</a>
                <a href="{{{ URL::to(\'admin/menuitem/\' . $id . \'/delete\' ) }}}" class="btn btn-sm btn-danger iframe"><span class="glyphicon glyphicon-trash"></span> {{ trans("admin/modal.delete") }}</a>
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
                MenuItem::where('id', '=', $value) -> update(array('position' => $order));
                $order++;
            }
        }
        return $list;
    }


    /**
     * Show a list of all the languages posts formatted for Datatables.
     *
     * @return Datatables JSON
     *
    public function data()
    {
        $menuitem = Menuitem::join('menu_categories', 'menu_categories.id', '=', 'menuitems.menu_category_id')
            ->select(array('menuitems.id','menuitems.title','menu_categories.title as category', 'languages.name', 
                'menuitems.created_at'));

        return Datatables::of($menuitem)
            ->add_column('actions', '<a href="{{{ URL::to(\'admin/menuitem/\' . $id . \'/edit\' ) }}}" class="btn btn-success btn-sm iframe" ><span class="glyphicon glyphicon-pencil"></span>  {{ trans("admin/modal.edit") }}</a>
                    <a href="{{{ URL::to(\'admin/menuitem/\' . $id . \'/delete\' ) }}}" class="btn btn-sm btn-danger iframe"><span class="glyphicon glyphicon-trash"></span> {{ trans("admin/modal.delete") }}</a>
                    <input type="hidden" name="row" value="{{$id}}" id="row">')
            ->remove_column('id')

            ->make();
    }
     * 
     * 
     */

    
}
