<?php

namespace mainBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * CalRes
 *
 * @ORM\Table(name="cal_res")
 * @ORM\Entity(repositoryClass="mainBundle\Repository\CalResRepository")
 */
class CalRes
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="lang", type="text")
     */
    private $lang;

    /**
 l    * @var string
     *
     * @ORM\Column(name="league", type="text")
     */
    private $league;


    /**
     * @var string
     *
     * @ORM\Column(name="lclass", type="text")
     */
    private $lclass;


    /**
     * @var int
     *
     * @ORM\Column(name="currweek", type="integer")
     */
    private $currweek;


    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set lang
     *
     * @param string $lang
     * @return CalRes
     */
    public function setLang($lang)
    {
        $this->lang = $lang;

        return $this;
    }

    /**
     * Get lang
     *
     * @return string 
     */
    public function getLang()
    {
        return $this->lang;
    }

    /**
     * Set league
     *
     * @param string $league
     * @return CalRes
     */
    public function setLeague($league)
    {
        $this->league = $league;

        return $this;
    }

    /**
     * Get league
     *
     * @return string 
     */
    public function getLeague()
    {
        return $this->league;
    }

    /**
     * Get lclass
     *
     * @return string
     */
    public function getLclass()
    {
        return $this->lclass;
    }

    /**
     * set lclass
     * @param string $lclass
     * @return CalRes
     */
    public function setLclass($lclass)
    {
        $this->lclass = $lclass;
        return $this;
    }

    /**
     * Set currweek
     *
     * @param integer $currweek
     * @return CalRes
     */
    public function setCurrweek($currweek)
    {
        $this->currweek = $currweek;

        return $this;
    }

    /**
     * Get currweek
     *
     * @return integer 
     */
    public function getCurrweek()
    {
        return $this->currweek;
    }
}
