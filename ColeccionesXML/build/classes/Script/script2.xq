for $a in //libro where count($a/autor)>1 return $a/titulo/text()