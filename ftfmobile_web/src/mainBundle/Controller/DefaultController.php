<?php

namespace mainBundle\Controller;


use Assetic\Exception\Exception;
use mainBundle\Entity\CalRes;
use mainBundle\mainBundle;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use Symfony\Component\DomCrawler\Crawler;

use mainBundle\Entity\News;
use mainBundle\Entity\NewsContent;
use mainBundle\Controller\SimpleImage;

//use Symfony\Component\HttpFoundation\Request;


use Symfony\Component\Security\AclExceptionException;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\GetSetMethodNormalizer;
use Symfony\Component\Serializer\Encoder\JsonEncoder;

class DefaultController extends Controller
{

// private $whitelist = array('apache2handler','cli');

    private $fr_n_tmp= array();
    private $fr_nc_tmp=array();

    private $ar_n_tmp= array();
    private $ar_nc_tmp=array();
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



    /**
     * @Route("/v1/handshake")
     * @Template()
     */

    public function handshakeAction()
    {
        echo "welcome_ftfmobile";
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



    /**
     * @Route("/v1/getnew/{id}")
     * @Template()
     */

    public function getnewAction($id)
    {
try{

        $em=$this->getDoctrine()->getManager();
        $connection = $em->getConnection();
        $statement = $connection->query('
        select * from news_content WHERE id = '.$id.';
        ');

        $res=$statement->fetchAll();
       $res = array('anew'=>$res);
       echo json_encode($res);
}

catch(\Exception $e)
{
$this->sendmail($e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

    /**
     * @Route("/v1/getnews/{lang}/{cat}/{start}/{end}")
     * @Template()
     */

    public function getnewsAction($lang,$cat,$start,$end)
    {
 /*       $lang = 'fr';
        $start = 11;
        $end = 20;
*/

try{
//        $qlang='\''.$lang.'\'';


        $em=$this->getDoctrine()->getManager();
        $selected_Entites = $em->getRepository('mainBundle:News')->findBy(array('lang' => $lang,'cat' => $cat));

        $size=sizeof($selected_Entites);

        if($size>0){

        $res = array_slice($selected_Entites,$start-1,$end);
        $res=array('news'=>$res);
        $serializer = new Serializer(array(new GetSetMethodNormalizer()), array('json' => new
        JsonEncoder()));
        $json = $serializer->serialize($res, 'json');

        echo $json;

//        }


            }
//        $connection = $em->getConnection();
//
//                $statement = $connection->query('
//                select min(id) as mini from news where lang like '.$qlang.';
//                ');
//
//                $res=$statement->fetchAll();
//                $min_index = $res[0]['mini'];
//                //echo $min_index;
//
//
//                $statement = $connection->query('
//                select max(id) as maxi from news where lang like '.$qlang.';
//                ');
//
//                $res=$statement->fetchAll();
//                $max_index = $res[0]['maxi'];

                //echo $max_index;
//        $qstart = $start + $min_index - 1;
//        $qend = $end + $min_index - 1;
//
//        if($qstart>=$min_index && $qend>$qstart){
//
//
//
//
//
//
//        $statement = $connection->query('
//        SELECT * FROM `news` WHERE lang like '.$qlang.' and id BETWEEN '.$qstart.' and '.$qend.';
//        ');
//
//        $res=$statement->fetchAll();
//        }else
//            $res = null;
//        //print_r($res);
//
//        $res = array('news'=>$res);
//        $serializer = new Serializer(array(new GetSetMethodNormalizer()), array('json' => new
//        JsonEncoder()));
//        $json = $serializer->serialize($res, 'json');
//
//        echo $json;

}catch(\Exception $e){
$this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}


    }
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



    /**
     * @Route("/v1/getlastweek/{lang}/{league}/{lclass}")
     * @Template()
     */

    public function getlastweekAction($lang,$league,$lclass)
    {
     try{
        $em = $this->getDoctrine()->getManager();
        $repository=$em->getRepository('mainBundle:CalRes');

        $entity = $repository->findOneBy(
            array('league' => $league, 'lang' => $lang, 'lclass'=>$lclass)
        );

        if ($entity== null){
            echo 0;
            //echo 'not found !';
            //$em=$this->getDoctrine()->getManager();

        }else{
            echo $entity->getCurrweek();
            //$em->flush();
            //echo 'found !';
        }

     }catch(\Exception $e){
         $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
     }
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

    /**
     * @Route("/sync_natteam/{lang}")
     * @Template()
     */

    public function sync_natteamAction($lang)
    {
        try{
        ini_set('max_execution_time', 1920);
//        $league="l1";$lclass="l1";$lang="ar";

        $natteam_reldir='v1/nationalteam/';
        $events_reldir=$natteam_reldir.'events/'.$lang.'/';
        $appointments_reldir=$natteam_reldir.'appointments/'.$lang.'/';

        $events_datadir='../data/'.$events_reldir;
        $events_tmpdatadir='../tmpdata/'.$events_reldir;

        $appointments_datadir='../data/'.$appointments_reldir;
        $appointments_tmpdatadir='../tmpdata/'.$appointments_reldir;


        $events_filename= $lang.'_events.html';
        $appointments_filename=$lang.'_appointments.html';

        /*        if(file_exists($tmpdatadir))
                    $this->del_dir_files($tmpdatadir);*/
            $event_patch_css='<html><head><meta charset="UTF-8">
                        </head>
						<style>
                        div>a>img{display:none;}
						.section_title{display:none;}
						a{text-decoration:none;font-weight: bold;color:black;pointer-events: none;}
						
						caption{display:none;}

						
						.event {
							float: left;
							 
							 
						}
						.event_drapeau1 {
							padding-left: 10px;
						text-align: center;
						}
						.event_drapeau2 {
							padding-left: 10px;
						text-align: center;
						}
						.event_drapeaux {
							float: left;
							 
							width: 100%;
							 
						}
						.event_title{
							float: left;
						font-size: 15px;
						font-weight: bold;
						margin-left: auto;
						margin-right: auto;
						width: 100%;
						}
						.vs {
							font-size: 28px;
						color: red;
						font-weight: bold;
						}
						.countdown_timer{
							float:left;
						}

						.compte_a_rebour{
							font-size: 15px;
						font-weight: bold;
						color: red;
						}
						</style>
                        <body>';

            $appointments_patch_css='<html><head><meta charset="UTF-8">
                        </head>
						<style>
                        div>a>img{display:none;}
						.section_title{display:none;}
						a{text-decoration:none;font-weight: bold;color:black;pointer-events: none;}
						.rendezvous{
							clear: both;
						}
						.rendezvous_date{
							float: left;
						-webkit-border-top-left-radius: 20px;
						-webkit-border-bottom-left-radius: 20px;
						-moz-border-radius-topleft: 20px;
						-moz-border-radius-bottomleft: 20px;
						border-top-left-radius: 20px;
						border-bottom-left-radius: 20px;
						background: rgb(207, 207, 207);
						width: 20%;
						}
						.rendezvous_date_day{
							float: left;
						font-size: 41px;
						font-weight: bold;
						padding: 11px;
						color: rgb(102, 102, 102);
						}
						.rendezvous_date_month{
							padding: 10px;
						color: rgb(102, 102, 102);
						font-size: 21px;
						font-weight: bold;
						line-height: 11px;
						text-transform: uppercase;
						}
						.rendezvous_icones{
							float: left;
						border: solid 1px rgb(215, 215, 215);
							width: 47%;
						}
						.rendezvous-icone{
							float: left;
							width: 50%;
							display: table-cell;
							vertical-align: middle;
							text-align: center;
						}
						 
						.rendezvous_matches{
						float: left;
						-webkit-border-top-right-radius: 20px;
						-webkit-border-bottom-right-radius: 20px;
						-moz-border-radius-topright: 20px;
						-moz-border-radius-bottomright: 20px;
						border-top-right-radius: 20px;
						border-bottom-right-radius: 20px;
						background-color: red;
						padding: 0px;
						font-weight: bold;
						color: white;
						padding-left: 3px;
						padding-right: 3px;
							width: 30%;
						}
						 
						.rendezvous_matche{
							padding: 5px;
							padding-left: 10px;
							padding-right: 10px;
							font-weight: bold;
							color: white;
							font-size: 12px;
							width: 58px;
						}
						</style>
                        <body>';

            $end_patchcss='</body></html> ';


        if($lang=='ar'){
            $url='http://www.ftf.org.tn/ar2/';
            $html=$this->get_web_page($url);
            $crawler=new Crawler($html);

            $events=$crawler->filter('div[class=container50left]')->html();

            $appointments=$crawler->filter('div[class=container50]')->html();

        }else{
            $url='http://www.ftf.org.tn/fr/';
            $html=$this->get_web_page($url);
            $crawler=new Crawler($html);

            $events=$crawler->filter('div[class="home-widget-1 grid one-half"]')->eq(2)->html();

            $appointments=$crawler->filter('div[class="home-widget-1 grid one-half"]')->eq(3)->html();
            }

            $appointments=$appointments_patch_css.$appointments.$end_patchcss;
            $events=$event_patch_css.$events.$end_patchcss;

            if(!is_dir($appointments_tmpdatadir)){
                mkdir($appointments_tmpdatadir, 0777, true);
            }
            file_put_contents($appointments_tmpdatadir.$appointments_filename,$appointments);

            if(!is_dir($events_tmpdatadir)){
                mkdir($events_tmpdatadir, 0777, true);
            }
            file_put_contents($events_tmpdatadir.$events_filename,$events);


            $this->del_dir_files($events_datadir);
            $this->del_dir_files($appointments_datadir);

        $this->Move_Files($events_tmpdatadir,$events_datadir);
        $this->Move_Files($appointments_tmpdatadir,$appointments_datadir);

        //set_time_limit(960);
        //return $this->render('mainBundle:Default:sync_news.html.twig');
        /*if(!file_exists('../isSync')){
            file_put_contents('../isSync', '1');*/







/*
            if($state){
                //$this->Move_Files($tmpdatadir,$datadir);
//                    echo 'sucess<br>';
            }else{
                echo 'Sync failed ! ';
                //unlink('../isSync');
            }*/





        }catch(\Exception $e){
            $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
        }
        return null;
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



    /**
     * @Route("/sync_calres/{lang}/{league}/{lclass}")
     * @Template()
     */

    public function sync_calresAction($lang,$league,$lclass)
    {



        //set_time_limit(960);
        //$league= 'l1';$lang='ar';$lclass='a';
        try{
            ini_set('max_execution_time', 1920);
            $reldir='v1/leagues/'.$league.'/calres/'.$lclass.'/'.$lang.'/';

            $datadir='../data/'.$reldir;

            $tmpdatadir='../tmpdata/'.$reldir;

            $bfname= $lang.'_'.$league.'_calres_'.$lclass.'_';





          /*if(is_dir($dir)){
                $this->del_dir_files($dir);
            }*/
            if (! is_dir($tmpdatadir)) {
                mkdir($tmpdatadir, 0777, true);
            }

          if($league=='l1'&&$lang=='ar'&&$lclass=='grpa')
               $url='http://www.ftf.org.tn/ar2/calandrier-resultats-ligue-1-groupe-a/';
               else if($league=='l1'&&$lang=='ar'&&$lclass=='grpb')
                   $url='http://www.ftf.org.tn/ar2/calandrier-et-resultats-ligue-1-groupe-b/';
               else if($league=='l1'&&$lang=='fr'&&$lclass=='grpa')
                   $url='http://www.ftf.org.tn/fr/calandrier-resultats-ligue-1-groupe-a/';
               else if($league=='l1'&&$lang=='fr'&&$lclass=='grpb')
                   $url='http://www.ftf.org.tn/fr/calandrier-et-resultats-ligue-1-groupe-b/';
               else if($league=='l2'&&$lang=='ar'&&$lclass=='grpa')
                   $url='http://www.ftf.org.tn/ar2/ligue-2-poule-a-calendrier-et-resultats/';
               else if($league=='l2'&&$lang=='ar'&&$lclass=='grpb')
                   $url='http://www.ftf.org.tn/ar2/ligue-2-poule-b-calendrier-et-resultats/';
               else if($league=='l2'&&$lang=='fr'&&$lclass=='grpa')
                   $url='http://www.ftf.org.tn/fr/ligue-2-poule-a-calendrier-et-resultats/';
               else if($league=='l2'&&$lang=='fr'&&$lclass=='grpb')
                   $url='http://www.ftf.org.tn/fr/ligue-2-poule-b-calendrier-et-resultats/';
               else if ($league=='l1'&&$lang=='ar'&&$lclass=='poff')
                   $url='http://www.ftf.org.tn/ar2/%d8%a7%d9%84%d8%b1%d9%88%d8%b2%d9%86%d8%a7%d9%85%d8%a9-%d9%88-%d8%a7%d9%84%d9%86%d8%aa%d8%a7%d8%a6%d8%ac-%d9%85%d8%b1%d8%ad%d9%84%d8%a9-%d8%a7%d9%84%d8%a8%d9%84%d8%a7%d9%8a-%d8%a7%d9%88%d9%81/';
               else if ($league=='l1'&&$lang=='ar'&&$lclass=='pout')
                   $url='http://www.ftf.org.tn/ar2/calandrier-et-resultats-ligue-1-play-out/';
               else if ($league=='l1'&&$lang=='fr'&&$lclass=='poff')
                   $url='http://www.ftf.org.tn/fr/calendrier-et-resultats-l1-playoff/';
               else if ($league=='l1'&&$lang=='fr'&&$lclass=='pout')
                   $url='http://www.ftf.org.tn/fr/calandrier-et-resultats-ligue-1-play-out/';
               else{
                   echo 'not exists !';
                   exit(0);
               }
                   //$url = 'http://www.ftf.org.tn/ar2/calandrier-resultats-ligue-1-groupe-a/';
           $html = $this->get_web_page($url);

            $crawler=new Crawler($html);
            $content=$crawler->filter('section[class=content]')->html();

            $patchcss='<html><head><meta charset="UTF-8">
                        </head>
                        <style>
						div>a>img{display:none;}
						
						div.ligue-title{font-size:15px;font-weight:bold;};
                        div.match_cont{background-color:white;}
						div.match_eq_time{color:black;font-size:10px;font-weight: bold;}
						div.match_res{color:black;font-size:15px}
                        html{height: auto;background-color:white;}
                        a{text-decoration:none;font-weight: bold;color:black;pointer-events: none;}
						div.leTous{height:auto;}
						body{font-size:70%;color:transparent;width:auto}
						
						.journee-title{display:none;}
						.ligue-title{display:none;}
						.wp-pagenavi{display:none;}
						.ligue_groupe{display:none;}
						.header_classement{display:none;}
						.archive_saison_title{display:none;}
						.archive_saison{display:none;}
						
						.leTous{
							padding:10px;
						}
						.match_cont {
							 
						border-top: solid 2px grey;
						border-bottom: solid 2px grey;
						min-height: 35px;
						height:auto;
						padding: 10px; 
						text-align: center;

						}


						.date_match_cont {
							background-color: red;
						color: white;
						font-weight: bold;
						padding: 5px;
						}

						 

						.match_eq_time {
							 float: left;
							font-size: 12px;
							font-weight: bold;
						}

						 

						.match_eq_flag {
							float:left;
							width:10%;
						}

						.match_eq_title {
							float:left;  
							width:25%;
						}
						.match_res {
						float: left;
						width: 10%;
						font-size: 32px;
						font-weight: bold;
						}
						
						.plus_details {
						float: right;
						width: 5%;
						font-size: 23px;
						font-weight: bold;
						text-align: right;
						}
						</style><body>';

            $patchjs='</body></html>';
            $content=$patchcss.$content.$patchjs;


            $day = $crawler->filter('span[class=current]')->text();

            file_put_contents($tmpdatadir.$bfname.$day.'.html',$content);

            $tmpday=$day-1;
            while($tmpday>0){
                $newurl = $url.'?journee='.$tmpday;

                $html = $this->get_web_page($newurl);
                $crawler=new Crawler($html);
                $content=$crawler->filter('section[class=content]')->html();
                $content=$patchcss.$content.$patchjs;
                file_put_contents($tmpdatadir.$bfname.$tmpday.'.html',$content);
                $tmpday--;
            }
            $this->del_dir_files($datadir);
            $this->Move_Files($tmpdatadir,$datadir);
            $em = $this->getDoctrine()->getManager();
            $repository=$em->getRepository('mainBundle:CalRes');

            $entity = $repository->findOneBy(
                array('league' => $league, 'lang' => $lang, 'lclass'=>$lclass)
            );

            if ($entity== null){
                //echo 'not found !';
                //$em=$this->getDoctrine()->getManager();
                $lcalres = new CalRes();

                $lcalres->setLeague($league);
                $lcalres->setLang($lang);
                $lcalres->setLclass($lclass);
                $lcalres->setCurrweek($day);

                $em->persist($lcalres);//exec sql
                $em->flush();//commit;
            }else{
                $entity->setCurrweek($day);
                $em->flush();
                //echo 'found !';
            }

            //echo $day;
            //            echo $html;
            }
catch(\Exception $e){
$this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}

        return null;
    }



    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



    /**
     * @Route("/sync_ranking/{lang}/{league}/{lclass}")
     * @Template()
     */

    public function sync_rankingAction($lang,$league,$lclass)
    {
        try{
        ini_set('max_execution_time', 1920);

//        $league="l1";$lclass="l1";$lang="ar";

        $reldir='v1/leagues/'.$league.'/ranking/'.$lclass.'/'.$lang.'/';

        $datadir='../data/'.$reldir;

        $tmpdatadir='../tmpdata/'.$reldir;

        $filename= $lang.'_'.$league.'_rk_'.$lclass.'.html';


/*        if(file_exists($tmpdatadir))
            $this->del_dir_files($tmpdatadir);*/


        if($league=='l1'&&$lclass==$league&&$lang=='ar'){

            $url='http://www.ftf.org.tn/ar2/classement-ligue-1-groupe-a/';
            $state=$this->download_leagueRankingContent($url,
                $tmpdatadir.'ar_l1_rk_ca.html',
                $tmpdatadir.'ar_l1_rk_cb.html');

        }else if($league=='l1'&&$lclass==$league&&$lang=='fr'){

            $url='http://www.ftf.org.tn/fr/classement-ligue-i/';
            $state=$this->download_leagueRankingContent($url,
                $tmpdatadir.'fr_l1_rk_ca.html',
                $tmpdatadir.'fr_l1_rk_cb.html');

        }else if($league=='l2'&&$lclass==$league&&$lang=='fr'){
            $url='http://www.ftf.org.tn/fr/ligue-2-group-b-resultats-et-classement/';

            $state=$this->download_leagueRankingContent($url,
                $tmpdatadir.'fr_l2_rk_ca.html',
                $tmpdatadir.'fr_l2_rk_cb.html');

        }else if($league=='l2'&&$lclass==$league&&$lang=='ar'){
            $url='http://www.ftf.org.tn/ar2/ligue-2-group-b-resultats-et-classement/';

            $state=$this->download_leagueRankingContent($url,
                $tmpdatadir.'ar_l2_rk_ca.html',
                $tmpdatadir.'ar_l2_rk_cb.html');

        }else if($league=='l1'&&$lclass=='poff'&&$lang=='ar'){

            $url='http://www.ftf.org.tn/ar2/';
            $state=$this->download_LeagueRankingContent($url,$tmpdatadir.$filename,null);

        }else if($league=='l1'&&$lclass=='pout'&&$lang=='ar'){

            $url='http://www.ftf.org.tn/ar2/';
            $state=$this->download_LeagueRankingContent($url,null,$tmpdatadir.$filename);

        }else if($league=='l1'&&$lclass=='poff'&&$lang=='fr'){

            $url='http://www.ftf.org.tn/fr/classment-ligue1-playoff/';
            $state=$this->download_LeagueRankingContent($url,$tmpdatadir.$filename,null);

        }else if($league=='l1'&&$lclass=='pout'&&$lang=='fr'){

            $url='http://www.ftf.org.tn/fr/classement-ligue-1-playout/';
            $state=$this->download_LeagueRankingContent($url,$tmpdatadir.$filename,null);
        }else{
            echo 'not exists !';
            exit(0);
        }


        //set_time_limit(960);
        //return $this->render('mainBundle:Default:sync_news.html.twig');
        /*if(!file_exists('../isSync')){
            file_put_contents('../isSync', '1');*/








                if($state){
                    $this->del_dir_files($datadir);
                    $this->Move_Files($tmpdatadir,$datadir);
//                    echo 'sucess<br>';
                    }else{
                        echo 'Sync failed ! ';
                        //unlink('../isSync');
                    }





        }catch(\Exception $e){
            $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
        }

        return null;
    }
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



    /**
     * @Route("/sync_gencal/")
     * @Template()
     */

    public function sync_gencalAction()
    {
        try{
            ini_set('max_execution_time', 1920);
            $url = 'http://www.ftf.org.tn/fr/calendriers/';
            $path_l1='/v1/leagues/l1/gencal/';
            $path_l2='/v1/leagues/l2/gencal/';

            $tmpdir_l1='../tmpdata'.$path_l1;
            $tmpdir_l2='../tmpdata'.$path_l2;

            $dir_l1='../data'.$path_l1;
            $dir_l2='../data'.$path_l2;

            $html = $this->get_web_page($url);

            $crawler=new Crawler($html);
            $content=$crawler->filter('p>a');

            $patch_begin = '<html><head><meta charset="UTF-8"></head><style>
                    img{display:block;width:100%;height:auto;margin : 0 auto;} 
                        </style><body>';
            $patch_end='</body></html>';


            $l1_a = $patch_begin.$content->eq(1)->html().'<br>'.$patch_end;
            $l1_b = $patch_begin.$content->eq(2)->html().'<br>'.$patch_end;
            $l2_a = $patch_begin.$content->eq(3)->html().'<br>'.$patch_end;
            $l2_b = $patch_begin.$content->eq(4)->html().'<br>'.$patch_end;

            if(!is_dir($tmpdir_l1.'ar/'))
                mkdir($tmpdir_l1.'ar/', 0777, true);

            file_put_contents($tmpdir_l1.'ar/ar_l1_a.html',$l1_a);
            file_put_contents($tmpdir_l1.'ar/ar_l1_b.html',$l1_b);



            if(!is_dir($tmpdir_l1.'fr/'))
                mkdir($tmpdir_l1.'fr/', 0777, true);

            file_put_contents($tmpdir_l1.'fr/fr_l1_a.html',$l1_a);
            file_put_contents($tmpdir_l1.'fr/fr_l1_b.html',$l1_b);


                                /*-------------------*/

            if(!is_dir($tmpdir_l2.'ar/'))
                mkdir($tmpdir_l2.'ar/', 0777, true);

            file_put_contents($tmpdir_l2.'ar/ar_l2_a.html',$l2_a);
            file_put_contents($tmpdir_l2.'ar/ar_l2_b.html',$l2_b);



            if(!is_dir($tmpdir_l2.'fr/'))
                mkdir($tmpdir_l2.'fr/', 0777, true);

            file_put_contents($tmpdir_l2.'fr/fr_l2_a.html',$l2_a);
            file_put_contents($tmpdir_l2.'fr/fr_l2_b.html',$l2_b);

            $this->Move_Files($tmpdir_l1.'ar/',$dir_l1.'ar/');
            $this->Move_Files($tmpdir_l1.'fr/',$dir_l1.'fr/');
            $this->Move_Files($tmpdir_l2.'ar/',$dir_l2.'ar/');
            $this->Move_Files($tmpdir_l2.'fr/',$dir_l2.'fr/');

//
//            echo $l1_a.'<br>';
//            echo $l1_b.'<br>';
//            echo $l2_a.'<br>';
//            echo $l2_b.'<br>';

        }catch (\Exception $e){

        }
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/



     /**
     * @Route("/sync_news/{lang}/{cat}")
     * @Template()
     */

    public function sync_newsAction($lang,$cat)
    {


        try{
            ini_set('max_execution_time', 1920);

            $rel_mobilenews_dir='v1/news/mobilenews/'.$cat.'/'.$lang.'/';
            $rel_newsimages_dir='v1/news/newsimages/'.$cat.'/'.$lang.'/';

            $mobilenews_data_dir='../data/'.$rel_mobilenews_dir;
            $mobilenews_tmpdata_dir ='../tmpdata/'.$rel_mobilenews_dir;

            $newsimages_data_dir='../data/'.$rel_newsimages_dir;
            $newsimages_tmpdata_dir ='../tmpdata/'.$rel_newsimages_dir;


        if($lang == 'ar' && $cat=='all'){
             $url='http://www.ftf.org.tn/ar2/category/actualites/page/';
                $state = $this->get_ar_news($url,$cat,1,$this->ar_n_tmp,$this->ar_nc_tmp);
            }else if($lang == 'fr' && $cat=='all'){
                $url='http://www.ftf.org.tn/fr/category/actualites/page/';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='l1'){
                $url='http://www.ftf.org.tn/ar2/category/ligue-1/page/';
                $state = $this->get_ar_news($url,$cat,1,$this->ar_n_tmp,$this->ar_nc_tmp);
            }else if($lang == 'fr' && $cat=='l1'){
                $url='http://www.ftf.org.tn/fr/category/ligue-1/page/';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='l2'){
                $url='http://www.ftf.org.tn/ar2/category/ligue-2/page/';
                $state = $this->get_ar_news($url,$cat,1,$this->ar_n_tmp,$this->ar_nc_tmp);
            }else if($lang == 'fr' && $cat=='l2'){
                $url='http://www.ftf.org.tn/fr/category/ligue-2/page/';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='l3'){
                $url='http://www.ftf.org.tn/ar2/category/ligue-3/page/';
                $state = $this->get_ar_news($url,$cat,1,$this->ar_n_tmp,$this->ar_nc_tmp);
            }else if($lang == 'fr' && $cat=='l3'){
                $url='http://www.ftf.org.tn/fr/category/ligue-3/page/';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='photos'){
                $url='http://www.ftf.org.tn/ar2/category/albums-photos/page';
                $state = $this->get_ar_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'fr' && $cat=='photos'){
                $url='http://www.ftf.org.tn/fr/category/albums-photos/page';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='videos'){
                $url='http://www.ftf.org.tn/ar2/category/albums-videos/page/';
                $state = $this->get_ar_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'fr' && $cat=='videos'){
                $url='http://www.ftf.org.tn/fr/category/albums-videos/page/';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='ad'){
                $url='http://www.ftf.org.tn/ar2/category/designation-des-arbitres/page/';
                $state = $this->get_ar_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'fr' && $cat=='ad'){
                $url='http://www.ftf.org.tn/fr/category/designation-des-arbitres/page/';
                $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
            }else if($lang == 'ar' && $cat=='ntd'){
            $url='http://www.ftf.org.tn/ar2/category/direction-technique/page/';
            $state = $this->get_ar_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
        }else if($lang == 'fr' && $cat=='ntd'){
            $url='http://www.ftf.org.tn/fr/category/direction-technique/page/';
            $state = $this->get_fr_news($url,$cat,1,$this->fr_n_tmp,$this->fr_nc_tmp);
        }
            else
                echo 'not found !';

                        if($state){

                        $this->delete_old_news($cat,$lang);

                        $this->store_news_db($this->ar_n_tmp,$this->ar_nc_tmp);
                        $this->store_news_db($this->fr_n_tmp,$this->fr_nc_tmp);

                        unset($this->ar_n_tmp);
                        unset($this->ar_nc_tmp);

                        unset($this->fr_n_tmp);
                        unset($this->fr_nc_tmp);

//                            $this->del_dir_files($newsimages_data_dir);
//                            $this->del_dir_files($mobilenews_data_dir);

                        $this->Move_Files($newsimages_tmpdata_dir,$newsimages_data_dir);
                        $this->Move_Files($mobilenews_tmpdata_dir,$mobilenews_data_dir);

                            echo 'sucess news <br>';


        }else{
            echo 'Sync news failed !';
                            $this->del_dir_files($newsimages_tmpdata_dir);
                            $this->del_dir_files($mobilenews_tmpdata_dir);
            //unlink('../isSync');
        }

            //unlink('../isSync');

/*
        }else{
            echo "Is Sync already !";
        }
*/


        }catch(\Exception $e){
            $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
        }
    return null;
    }


/*------------------------------------------------------------------------------------------------------------------------------------------------------*/
private function get_fr_news($url,$cat,$lim,&$news_array,&$newscontent_array){
    try{
    $sucess = true;

    $rel_mobilenews_dir='v1/news/mobilenews/'.$cat.'/fr/';
    $rel_newsimages_dir='v1/news/newsimages/'.$cat.'/fr/';

    $mobilenews_data_dir='../data/'.$rel_mobilenews_dir;
    $mobilenews_tmpdata_dir ='../tmpdata/'.$rel_mobilenews_dir;

    $newsimages_data_dir='../data/'.$rel_newsimages_dir;
    $newsimages_tmpdata_dir ='../tmpdata/'.$rel_newsimages_dir;


        for ($i = 1; $i <= $lim; $i++) {
        $html = $this->get_web_page($url.$i);

        if($html != null){




               $crawler=new Crawler($html);
               $number=$crawler->filter('h2[class=post-title]>a')->count();

               for ($j = 1; $j <= $number;$j++) {





                $title=$crawler->filter('h2[class=post-title]>a')->eq($j-1)->text();
//                echo $title . '<br>';

                $imgurl=$crawler->filter('div[class=post-thumbnail]>a>img')->eq($j-1)->attr('src');
//                echo $imgurl . '<br>';

                $date=$crawler->filter('p[class=post-date]')->eq($j-1)->text();
//                echo $date . '<br>';


                $newurl=$crawler->filter('h2[class=post-title]>a')->eq($j-1)->attr('href');
//                echo $newurl . '<br>';



                    $new=new News();

                    $new->setLang('fr');
                    $new->setCat($cat);
                    $new->setTitle($title);
                    $new->setDate($date);

                        $imgfilename= basename($imgurl);
                        $lp= strrpos($imgfilename,'.');
                        $extension = substr($imgfilename,$lp);

                        $imgfilename = 'fr_'.$i.$j.$extension;

                    $this->downloadImgFromUrl($imgurl,$newsimages_tmpdata_dir.$imgfilename);


                    $new->setImgurl('/data/'.$rel_newsimages_dir.$imgfilename);

                    array_push($news_array,$new);







                   //echo '<br> id = '.$new->getId().'<br>';


            $newscontent = new NewsContent();
            $newscontent->setWebSiteNewUrl($newurl);
            $this->downloadNewContent($newurl,$mobilenews_tmpdata_dir.'fr_'.$i.$j.'.html');
            $newscontent->setMobileNewUrl('/data/'.$rel_mobilenews_dir.'fr_'.$i.$j.'.html');

            array_push($newscontent_array,$newscontent);









               }


            }else{
            $sucess = false;
            $this->del_dir_files($newsimages_tmpdata_dir);
            $this->del_dir_files($mobilenews_tmpdata_dir);
        }



    }
    }catch (\Exception $e){
        $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
        $this->del_dir_files($newsimages_tmpdata_dir);
        $this->del_dir_files($mobilenews_tmpdata_dir);
        return false;
    }
return $sucess;

}

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    private function get_ar_news($url,$cat,$lim,&$news_array,&$newscontent_array){

        try{
        $sucess = true;
        $rel_mobilenews_dir='v1/news/mobilenews/'.$cat.'/ar/';
        $rel_newsimages_dir='v1/news/newsimages/'.$cat.'/ar/';

        $mobilenews_data_dir='../data/'.$rel_mobilenews_dir;
        $mobilenews_tmpdata_dir ='../tmpdata/'.$rel_mobilenews_dir;

        $newsimages_data_dir='../data/'.$rel_newsimages_dir;
        $newsimages_tmpdata_dir ='../tmpdata/'.$rel_newsimages_dir;

        for ($i = 1; $i <= $lim; $i++) {
            $html = $this->get_web_page($url.$i);

            if($html != null){




                $crawler=new Crawler($html);
                $number =$crawler->filter('h2[class=post-title]>a')->count();

                for ($j = 1; $j <= $number;$j++) {




                        $title=$crawler->filter('h2[class=post-title]>a')->eq($j-1)->text();
//                echo $title . '<br>';

                        $imgurl=$crawler->filter('div[class=cat_image_tumb]>img')->eq($j-1)->attr('src');
//                echo $imgurl . '<br>';

                        $date=$crawler->filter('p[class=post-date]')->eq($j-1)->text();
//                echo $date . '<br>';


                        $newurl=$crawler->filter('h2[class=post-title]>a')->eq($j-1)->attr('href');
//                echo $newurl . '<br>';



                        $new=new News();

                        $new->setLang('ar');
                        $new->setCat($cat);
                        $new->setTitle($title);
                        $new->setDate($date);

                        $imgfilename= basename($imgurl);
                        $lp= strrpos($imgfilename,'.');
                        $extension = substr($imgfilename,$lp);

                        $imgfilename = 'ar_'.$i.$j.$extension;
//                        if(!file_exists('../tmpdata/v1/newsimages/'.$imgfilename))
                        $this->downloadImgFromUrl($imgurl,$newsimages_tmpdata_dir.$imgfilename);



                        $new->setImgurl('/data/'.$rel_newsimages_dir.$imgfilename);

                        array_push($news_array,$new);







                        //echo '<br> id = '.$new->getId().'<br>';


                        $newscontent = new NewsContent();
                        $newscontent->setWebSiteNewUrl($newurl);
                        $this->downloadNewContent($newurl,$mobilenews_tmpdata_dir.'ar_'.$i.$j.'.html');
                        $newscontent->setMobileNewUrl('/data/'.$rel_mobilenews_dir.'ar_'.$i.$j.'.html');

                        array_push($newscontent_array,$newscontent);








                }


            }else{
                $sucess = false;
                $this->del_dir_files($newsimages_tmpdata_dir);
                $this->del_dir_files($mobilenews_tmpdata_dir);
            }



        }
        }catch (\Exception $e){
            $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
            $this->del_dir_files($newsimages_tmpdata_dir);
            $this->del_dir_files($mobilenews_tmpdata_dir);
            return false;
        }
        return $sucess;

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

private function downloadNewContent($newurl,$outFileName){
    $doc = null;

    try{
        $html = $this->get_web_page($newurl);

        $crawler=new Crawler($html);
        $content=$crawler->filter('article')->html();
        $doc = '<html><head><meta charset="UTF-8"></head><style>
                    img{display:block;width:100%;height:auto;margin : 0 auto;} 
                    p{font-size:30px;}
                    h1{font-size:50px;}
                    div.pf-content{font-size:30px;}
                    iframe{display:block;width:100%;margin : 10px auto;}
                    table{width:100%;font-size:30px;}
                   
                   	.juiz_sps_links_list,
					.printfriendly,
					.pf-alignright,
					.creen-reader-text,
					.juiz_sps_maybe_hidden_text{display:none;}
                   
                    div>a>img{display:none;}
                   </style><body>'.$content.'</body></html>';

        $lp= strrpos($outFileName,'/');
        $dir = substr($outFileName,0,$lp);

        if (! is_dir($dir)) {
            mkdir($dir, 0777, true);
        }

        file_put_contents($outFileName, $doc);
    }catch (\Exception $e){
        $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
        //return null;
    }

//return $doc;
}



/*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    private function download_LeagueRankingContent($LRurl,$lcaFileName,$lcbFileName){
        try{
            $doc = null;
            $html = $this->get_web_page($LRurl);

            $crawler=new Crawler($html);
            $content=$crawler->filter('div[class=cp-table-wrap]');

            $patch = '<html><head><meta charset="UTF-8">
                        </head><style>	
                        div>a>img{display:none;}
						
                        a{color:black;font-weight:bold;text-decoration:none;pointer-events: none;}
                        caption{display:none;}
						th{background-color:white;}
						
						.event {float: left;}
						.last { clear: right; margin-right: 0!important; }
						
						.cp-table-wrap,
						.wpcm-pa-table-wrap { float: left; margin-bottom: 30px; width: 100%; overflow: auto; }
						.cp-table-full { width: 100%; }
						table.cp-table { border-collapse: separate;
						border-spacing: 3px;
						font-size: 0.875em;
						margin: 0 auto 15px;
						min-width: 86%;
						vertical-align: middle;
						background-color: #eee;}
						table.wpcm-pa-table { margin: 0 0 15px !important;width: 100% !important; }
						table.cp-table caption {
							   font-size: 45px;
							font-weight:bold;
							min-height:50px;	
							padding-bottom: 10px;
							padding-top: 10px;
							margin-bottom: 5px;
							color: white;
							padding-right: 12px;
							background-color: #e70013;
							 text-align: center; 
							 }
						table.cp-table td {vertical-align: middle; }
						table.cp-table td,
						table.cp-table th { font-weight:normal;font-size:30px;text-align: center;border: 0; }
						table.cp-table tbody tr td,
						table.cp-table tbody tr th { background: #fff; }
						table.cp-table tbody tr:nth-of-type(even) td { background: #fdfdfd; }
						table.cp-table tfoot th,
						table.cp-table tfoot td { background: #f9f9f9; }
						table.cp-table thead tr th.cp-stats,
						table.cp-table thead tr th.cp-player-stat,
						table.cp-table thead tr th.cp-match-stat { background-color: red; color: #fff; padding: 9px 4px; }
						table.cp-table td.pos { background-color: red !important; color: #fff; font-weight: 600; padding: 9px 4px; }
						table.cp-table thead tr th a { color: #fff; }
						table.cp-table .name,
						table.cp-table .position,
						table.cp-table .club,
						table.cp-table .opponent,
						table.cp-table .wpcm-date,
						table.cp-table td.notes { text-align: left; }
						table.cp-table td.pts { background: #f9f9f9 !important; font-weight: 600; }
						table.cp-table thead tr th.thumb,
						table.cp-table thead tr th.flag,
						table.cp-table thead tr th.name,
						table.cp-table thead tr th.number,
						table.cp-table thead tr th.position,
						table.cp-table thead tr th.age,
						table.cp-table thead tr th.height,
						table.cp-table thead tr th.weight,
						table.cp-table thead tr th.team,
						table.cp-table thead tr th.season,
						table.cp-table thead tr th.dob,
						table.cp-table thead tr th.hometown,
						table.cp-table thead tr th.joined,
						table.cp-table thead tr th.experience { background-color: transparent !important; color: #444; }
						table.cp-table span.sub { color: #aaa; margin-left: 8px; font-size: 0.875em; font-style: italic; }
						table.cp-table tr td.club-thumb img { height: 48px;width:auto }
						table.cp-player-stats-table tbody tr th { min-width: 100px; }
						table.cp-table .thumb a img:hover { opacity: 0.75; }
						table.cp-table .thumb a img { -webkit-transition: all 1s ease; -moz-transition: all 1s ease; transition: all 1s ease; }
                        </style><body>';

            if($lcaFileName!=null){

            $lca = $content->eq(0)->html();
            $lca = $patch.$lca.'</body></html>';

            $lp= strrpos($lcaFileName,'/');
            $dir = substr($lcaFileName,0,$lp);

            if (! is_dir($dir)) {
                mkdir($dir, 0777, true);
            }

            file_put_contents($lcaFileName, $lca);

            }

            if($lcbFileName!=null){
                $lcb = $content->eq(1)->html();
                $lcb = $patch.$lcb.'</body></html>';

            $lp= strrpos($lcbFileName,'/');
            $dir = substr($lcbFileName,0,$lp);

            if (! is_dir($dir)) {
                mkdir($dir, 0777, true);
            }
                file_put_contents($lcbFileName, $lcb);
            }


            return true;
        }catch (\Exception $e){
            $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
            return false;
        }

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

    function downloadImgFromUrl($url, $outFileName)
    {
       try{

        $lp= strrpos($outFileName,'/');
        $dir = substr($outFileName,0,$lp);

        if (! is_dir($dir)) {
            mkdir($dir, 0777, true);
        }


        if(is_file($url)) {
            copy($url, $outFileName);
        } else {
            $options = array(
                CURLOPT_FILE    => fopen($outFileName, 'w'),
                CURLOPT_TIMEOUT =>  28800, // set this to 8 hours so we dont timeout on big files
                CURLOPT_URL     => $url
            );

            $ch = curl_init();
            curl_setopt_array($ch, $options);
            curl_exec($ch);
            curl_close($ch);
//            $size = getimagesize($outFileName);
            try{


            $image = new SimpleImage();
            $image->load($outFileName);
            $image->resize(300, 150);
            $image->save($outFileName);

            }catch(\Exception $f){
                return;

            }
        }
       }catch (\Exception $e){
           $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
       }
       }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private function get_web_page($url){


            try{
                return file_get_contents($url);
            }catch (\Exception $e){
                $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
            }


    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private function Move_Files($source,$destination){
//        $source = "source/";
//        $destination = "destination/";
try{
        if (!is_dir($destination)) {
            mkdir($destination, 0777, true);
        }
    // Get array of all source files
        $files = scandir($source);
// Identify directories
// Cycle through all source files
    foreach ($files as $file) {
        if (in_array($file, array(".",".."))) continue;
        // If we copied this successfully, mark it for deletion
        if (copy($source.$file, $destination.$file)) {
            $delete[] = $source.$file;
        }

    }
// Delete all successfully-copied files
    foreach ($delete as $file) {
        unlink($file);
    }
}catch (\Exception $e){
    $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}
    }
    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

private  function  del_dir_files($dir){
try{
    if(file_exists($dir)){
    foreach(glob("{$dir}/*") as $file)
    {
        if(is_file($file)) {
            unlink($file);
            } else {
            $this->del_dir_files($file);
        }
    }
    rmdir($dir);
}
}catch (\Exception $e){
    $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}

}


    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/


    private function delete_old_news($cat,$lang){
try{
        $em = $this->getDoctrine()->getManager();
/*        $connection = $em->getConnection();
        $statement = $connection->exec('
        DELETE FROM news;
        ');*/

//        $connection->exec('ALTER TABLE news AUTO_INCREMENT = 1;');

    $selected_Entites = $em->getRepository('mainBundle:News')->findBy(array('lang' => $lang,'cat' => $cat));


    for($i=0;$i<sizeof($selected_Entites);$i++)
    $em->remove($selected_Entites[$i]);

    $em->flush();



        $this->del_dir_files('../data/v1/newsimages/'.$cat.'/'.$lang);
        $this->del_dir_files('../data/v1/mobilenews/'.$cat.'/'.$lang);
}catch (\Exception $e){
//    $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}

}

    private function store_news_db($news,$news_c){
try{
        $end = sizeof($news);
        for($i=0;$i<$end;$i++){

            $em=$this->getDoctrine()->getManager();
            $em->persist($news[$i]);//exec sql
            $em->flush();//commit;

          $newscontentid = $em->getRepository(News::class)
                    ->findOneById($news[$i]->getId());
          $news_c[$i]->setId($newscontentid);

          $em->persist($news_c[$i]);//exec sql
          $em->flush();//commit;
        }
}catch (\Exception $e){
    $this->sendmail( $e->getLine(),$e->getMessage(),$_SERVER['REQUEST_URI']);
}

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

    public function sendmail($code,$error,$link)
    {

        $transport = \Swift_SmtpTransport::newInstance("smtp.gmail.com",465,"ssl")
        ->setUsername("ftfmobile@gmail.com")->setPassword("qwerty_24");

        $mailer = \Swift_Mailer::newInstance($transport);

        date_default_timezone_set('Africa/Tunis');

        $message = \Swift_Message::newInstance("Error Mail")
            ->setSubject("API Exception line : ".$code)
            ->setFrom('ftfmobile@gmail.com')
            ->setTo('ftfmobile@gmail.com')
            ->setBody(
                $this->renderView(
                // app/Resources/views/Emails/registration.html.twig
                    'mail_template.html.twig',
                    array('error' => $error,'link'=>$link,'time'=>gmdate("Y/m/j H:i:s", time() + 1*3600))
                ),
                'text/html'
            )
            /*
             * If you also want to include a plaintext version of the message
            ->addPart(
                $this->renderView(
                    'Emails/registration.txt.twig',
                    array('name' => $name)
                ),
                'text/plain'
            )
            */
        ;

        $mailer->send($message);

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/

}