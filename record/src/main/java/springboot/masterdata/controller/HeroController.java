package springboot.masterdata.controller;

import org.springframework.web.bind.annotation.*;
import springboot.base.controller.BaseController;
import springboot.base.entity.JsonObjectResult;
import springboot.util.ActionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试angularJs用
 */
@RequestMapping("/hero")
@RestController
public class HeroController extends BaseController {

    public static Hero[] heroes = new Hero[]{
            new Hero("1","钢铁侠"),
            new Hero("2","美国队长"),
            new Hero("3","黑寡妇"),
            new Hero("4","蜘蛛侠"),
            new Hero("5","绿巨人"),
            new Hero("6","雷神"),
            new Hero("7","猎鹰"),
            new Hero("8","战争机器"),
            new Hero("9","奇异博士"),
            new Hero("10","鹰眼"),
            new Hero("11","金刚狼")
    };
    public static int id = 12;

    @GetMapping("/list")
    public JsonObjectResult getHeroes(){
        return ActionUtil.sendResult(heroes);
    }

    @GetMapping("/getHero/{id}")
    public JsonObjectResult getHero(@PathVariable("id") String id){
        for(Hero hero : heroes){
            if (hero.getId().equals(id))
                return ActionUtil.sendResult(hero);
        }
        return ActionUtil.sendResult("");
    }

    @GetMapping("/search/{name}")
    public List<Hero> searchHero(@PathVariable("name") String name){
        List<Hero> heroeList = new ArrayList<Hero>();
        for(Hero hero : heroes){
            if (hero.getName().indexOf(name)>0)
                heroeList.add(hero);
        }
//        return ActionUtil.sendResult(heroeList);
        return heroeList;
    }

    @PostMapping("/saveHero")
    public JsonObjectResult saveHero( @RequestBody Hero hero){
        for(Hero h : heroes){
            if (h.getId().equals(hero.getId()))
                h.setName(hero.getName());
        }
        return ActionUtil.sendResult();
    }

    @PostMapping("/addHero")
    public JsonObjectResult addHero(@RequestBody String name){
        List<Hero> heroList = new ArrayList<>();
        for(Hero h: heroes){
            heroList.add(h);
        }
        Hero add = new Hero(id++ +"", name);
        heroList.add(add);
        heroes = heroList.toArray(new Hero[heroList.size()]);
        return ActionUtil.sendResult(add);
    }

    @PostMapping("/deleteHero")
    public JsonObjectResult deleteHero(@RequestBody String id){
        List<Hero> heroList = new ArrayList<>();
        for(Hero h: heroes){
            if(!h.getId().equals(id))
                heroList.add(h);
        }
        heroes = heroList.toArray(new Hero[heroList.size()]);
        return ActionUtil.sendResult();
    }

}

class Hero{
    private String id;
    private String name;

    public Hero() {
    }

    public Hero(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}