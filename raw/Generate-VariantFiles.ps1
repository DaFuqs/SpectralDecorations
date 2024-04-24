$BlockNames = @("oak",
"spruce",
"birch",
"dark_oak",
"jungle",
"acacia",
"bamboo",
"mangrove",
"cherry",
"crimson",
"warped")



function Get-LangEntries([string[]] $Names) {
    $Names | Foreach-Object {
        $words = $_ -split "_"
        $resultingWords = @()
        foreach($word in $words) {
            $resultingWords += ([string] $word[0]).ToUpper() + $word.Substring(1)
        }
        $translation = $resultingWords -join " "
        "`"block.spectrum.$_`": `"$translation`","
    }
}


function Get-BlockStateBeam($Name) {
    Write-Output @"
{
  "variants": {
    "axis=x": {
      "model": "spectral-decorations:block/$Name`_beam",
      "x": 90,
      "y": 90
    },
    "axis=y": {
      "model": "spectral-decorations:block/$Name`_beam"
    },
    "axis=z": {
      "model": "spectral-decorations:block/$Name`_beam",
      "x": 90
    }
  }
}
"@
}

        
function Get-BlockStateAmphora($Name) {
    Write-Output @"
{
	"variants": {
		"facing=down,open=false": {
			"model": "spectral-decorations:block/$Name`_amphora",
			"x": 180
		},
		"facing=down,open=true": {
			"model": "spectral-decorations:block/$Name`_amphora_open",
			"x": 180
		},
		"facing=east,open=false": {
			"model": "spectral-decorations:block/$Name`_amphora",
			"x": 90,
			"y": 90
		},
		"facing=east,open=true": {
			"model": "spectral-decorations:block/$Name`_amphora_open",
			"x": 90,
			"y": 90
		},
		"facing=north,open=false": {
			"model": "spectral-decorations:block/$Name`_amphora",
			"x": 90
		},
		"facing=north,open=true": {
			"model": "spectral-decorations:block/$Name`_amphora_open",
			"x": 90
		},
		"facing=south,open=false": {
			"model": "spectral-decorations:block/$Name`_amphora",
			"x": 90,
			"y": 180
		},
		"facing=south,open=true": {
			"model": "spectral-decorations:block/$Name`_amphora_open",
			"x": 90,
			"y": 180
		},
		"facing=up,open=false": {
			"model": "spectral-decorations:block/$Name`_amphora"
		},
		"facing=up,open=true": {
			"model": "spectral-decorations:block/$Name`_amphora_open"
		},
		"facing=west,open=false": {
			"model": "spectral-decorations:block/$Name`_amphora",
			"x": 90,
			"y": 270
		},
		"facing=west,open=true": {
			"model": "spectral-decorations:block/$Name`_amphora_open",
			"x": 90,
			"y": 270
		}
	}
}
"@
}


function Get-BlockModelBeam($Name) {
    Write-Output @"
{
  "parent": "minecraft:block/cube_column",
  "textures": {
    "end": "spectral-decorations:block/vanilla_wood_beams/$Name`_top",
    "side": "spectral-decorations:block/vanilla_wood_beams/$Name"
  }
}
"@
}


function Get-BlockModelAmphora($Name) {
    Write-Output @"
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "spectral-decorations:block/vanilla_wood_amphoras/$Name`_top",
    "side": "spectral-decorations:block/vanilla_wood_amphoras/$Name",
    "top": "spectral-decorations:block/vanilla_wood_amphoras/$Name`_top"
  }
}
"@
}

function Get-BlockModelAmphoraOpen($Name) {
    Write-Output @"
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "spectral-decorations:block/vanilla_wood_amphoras/$Name`_top",
    "side": "spectral-decorations:block/vanilla_wood_amphoras/$Name",
    "top": "spectral-decorations:block/vanilla_wood_amphoras/$Name`_top_open"
  }
}
"@
}

function Get-ItemModel($Name) {
    Write-Output @"
{
    "parent": "spectral-decorations:block/$Name"
}
"@
        }

function Get-LootTable($Name) {
    Write-Output @"
{
	"type": "minecraft:block",
	"pools": [{
		"rolls": 1,
		"bonus_rolls": 0.0,
		"entries": [{
			"type": "minecraft:item",
			"name": "spectral-decorations:$Name"
		}],
		"conditions": [{
			"condition": "minecraft:survives_explosion"
		}]
	}]
}
"@
}

$Destination = $PSScriptRoot

Get-LangEntries -Names $BlockNames
Write-Output ""
Write-Output "- Mineable Block tags"
Write-Output "- Guidebook Entry"
Write-Output "- Recipes"
Write-Output "- Item Group"

$BlockNames | ForEach-Object {
    "`"spectral-decorations:$_`_beam`""

    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\blockstates\") -Name "$_`_beam.json" -ItemType File -Force -Value $(Get-BlockStateBeam -Name $_) | Out-Null
    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\blockstates\") -Name "$_`_amphora.json" -ItemType File -Force -Value $(Get-BlockStateAmphora -Name $_) | Out-Null
    
    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\models\block\") -Name "$_`_beam.json" -ItemType File -Force -Value $(Get-BlockModelBeam -Name $_) | Out-Null
    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\models\block\") -Name "$_`_amphora.json" -ItemType File -Force -Value $(Get-BlockModelAmphora -Name $_) | Out-Null
    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\models\block\") -Name "$_`_amphora_open.json" -ItemType File -Force -Value $(Get-BlockModelAmphoraOpen -Name $_) | Out-Null

    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\models\item\") -Name "$_`_beam.json" -ItemType File -Force -Value $(Get-ItemModel -Name "$_`_beam") -ErrorAction SilentlyContinue | Out-Null
    New-Item -Path $(Join-Path -Path $destination -ChildPath "\resources\assets\spectral-decorations\models\item\") -Name "$_`_amphora.json" -ItemType File -Force -Value $(Get-ItemModel -Name "$_`_amphora") -ErrorAction SilentlyContinue | Out-Null

    New-Item -Path $(Join-Path -Path $destination -ChildPath "\data\spectral-decorations\loot_tables\blocks\") -Name "$_`_beam.json" -ItemType File -Force -Value $(Get-LootTable -Name "$_`_beam") | Out-Null
    New-Item -Path $(Join-Path -Path $destination -ChildPath "\data\spectral-decorations\loot_tables\blocks\") -Name "$_`_amphora.json" -ItemType File -Force -Value $(Get-LootTable -Name "$_`_amphora") | Out-Null
}